package cs2030.simulator; 

import java.util.Optional;

/**
 * Arrive event class. 
 */
public class ArriveEvent extends Event {   
    /**
     * Arrive Event constructor for Level 1,2. 
     * Generated when a customer arrives. 
     * @param customer Takes in 1 customer 
     */
    public ArriveEvent(Customer customer) {  
        super(shop -> { 
            Optional<Server> availableServer = shop.find(x -> x.isAvailable());       
            Optional<Server> availableQueue = shop.find(x -> !x.getHasWaitingCustomer()); 
            if (availableServer.isPresent()) { 
                Server server = availableServer.get(); 
                return new Pair<Shop, Event>(shop, new ServeEvent(customer, server)); 
            } else if (availableQueue.isPresent()) { 
                Server server = availableQueue.get(); 
                return Pair.of(shop, new WaitEvent(customer, server));
            } else { 
                return Pair.of(shop, new LeaveEvent(customer)); 
            }
        }, customer, customer.getArrivalTime()); 
    } 
    
    /**
     * Arrive Event constructor for Level 3 - 7. 
     * Generated when a customer arrives. 
     * @param customer Takes in 1 customer
     * @param rng Takes in 1 Random Generator 
     */
    public ArriveEvent(Customer customer, MyRandomGenerator rng) {  
        super(shop -> { 
            Optional<Server> availableServer = shop.find(x -> x.isAvailable());       
            Optional<Server> availableQueue = shop.find(x -> !x.hasFullQueue()); 
            Optional<Server> shortestQueue = shop.getServerList()
                    .stream()
                    .reduce((s1, s2) -> s1.getCustomerQueueSize() <= s2.getCustomerQueueSize() 
                    ? s1 
                    : s2);
            if (availableServer.isPresent()) { 
                return Pair.of(shop, new ServeEvent(customer, availableServer.get(), rng)); 
            } else if (availableQueue.isPresent()) { 
                if (customer.isGreedy()) { 
                    return Pair.of(shop, new WaitEvent(customer, shortestQueue.get(), rng)); 
                } else { 
                    return Pair.of(shop, new WaitEvent(customer, availableQueue.get(), rng));
                } 
            } else { 
                return Pair.of(shop, new LeaveEvent(customer)); 
            }
        }, customer, customer.getArrivalTime()); 
    } 

    @Override
    public String toString() {
        return String.format("%.3f", super.getEventStartTime()) + 
                " " + super.getCustomer() + " arrives";  
    } 
}