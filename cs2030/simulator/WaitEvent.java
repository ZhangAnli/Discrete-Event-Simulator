package cs2030.simulator; 

/**
 * Wait event class. 
 */
public class WaitEvent extends Event { 

    /**
     * Overloaded constructor for Wait Event for Level 1, 2. 
     * Generated when a customer waits. 
     * @param customer Takes in 1 customer
     * @param server Takes in 1 server 
     */
    public WaitEvent(Customer customer, Server server) { 
        super(shop -> { 
            server.getCustomerQueue().add(customer); 
            Shop updatedShop = shop.replace(new Server(server.getId(), 
                    false, server.getNextAvailableTime(), 
                    server.getQueueSize(), server.getCustomerQueue())); 
            Server updatedServer = updatedShop.find(x -> x.isSameServer(server)).get(); 
            return new Pair<Shop, Event>(updatedShop, new ServeEvent(customer, updatedServer)); 
        }, customer, customer.getArrivalTime(), server); 
    } 

    /**
     * Overloaded constructor for Wait Event for Level 3 - 7. 
     * Generated when a customer waits. 
     * @param customer Takes in 1 customer
     * @param server Takes in 1 server 
     * @param rng Takes in 1 random generator
     */
    public WaitEvent(Customer customer, Server server, MyRandomGenerator rng) { 
        super(shop -> { 
            server.getCustomerQueue().add(customer); 
            Shop updatedShop; 
            if (server instanceof SelfCheckOut) { 
                updatedShop = shop.replace(new SelfCheckOut(server.getId(), 
                        false, server.getNextAvailableTime(), 
                        server.getQueueSize()), rng); 
            } else { 
                updatedShop = shop.replace(new Server(server.getId(), 
                        false, server.getNextAvailableTime(), 
                        server.getQueueSize(), server.getCustomerQueue()), rng); 
            }
            return Pair.of(updatedShop, null); 
        }, customer, customer.getArrivalTime(), server); 
    } 
 
    @Override
    public String toString() {
        if (super.getServer() instanceof SelfCheckOut) { 
            return String.format("%.3f", super.getEventStartTime()) + 
                    " " + super.getCustomer() + 
                    " waits to be served by self-check " + server.getId(); 
        } else { 
            return String.format("%.3f", super.getEventStartTime()) + 
                    " " + super.getCustomer() + 
                    " waits to be served by server " + server.getId(); 
        }
    } 
} 