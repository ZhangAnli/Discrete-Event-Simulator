package cs2030.simulator; 

/**
 * Serve event class. 
 */
public class ServeEvent extends Event { 
    
    /**
     * Overloaded Serve Event constructor.
     * Generated when the customer is served.  
     * @param customer Takes in 1 customer
     * @param server Takes in 1 server
     */
    public ServeEvent(Customer customer, Server server) { 
        super(shop -> { 
            shop.replace(new Server(server.getId(), false, 
                    customer.getArrivalTime() > server.getNextAvailableTime()
                    ? customer.getArrivalTime() + 1.0
                    : server.getNextAvailableTime() + 1.0, 
                    server.getQueueSize(), server.getCustomerQueue())); 
            Server updatedServer = shop.find(s -> s.isSameServer(server)).get(); 
            return Pair.of(shop, (new DoneEvent(customer, updatedServer))); 
        }, customer,customer.getArrivalTime() > server.getNextAvailableTime() 
                ? customer.getArrivalTime() 
                : server.getNextAvailableTime()); 
    } 
    
    /**
     * Overloaded Serve Event constructor. 
     * Generated when the customer is served. 
     * @param customer Takes in 1 customer
     * @param server Takes in 1 server 
     * @param rng Takes in 1 random generator
     */
    public ServeEvent(Customer customer, Server server, MyRandomGenerator rng) { 
        super(shop -> {
            Server updatedServer = shop.find(s -> s.isSameServer(server)).get(); 
            updatedServer.getCustomerQueue().poll(); 
            Shop updatedShop;
            if (server instanceof SelfCheckOut) { 
                updatedShop = shop.replace(new SelfCheckOut(server.getId(), false, 
                    customer.getArrivalTime() > server.getNextAvailableTime() 
                    ? customer.getArrivalTime() + rng.genServiceTime() 
                    : server.getNextAvailableTime() + rng.genServiceTime(), 
                    server.getQueueSize()), rng); 
            } else { 
                updatedShop = shop.replace(new Server(server.getId(), false, 
                    customer.getArrivalTime() > server.getNextAvailableTime() 
                    ? customer.getArrivalTime() + rng.genServiceTime() 
                    : server.getNextAvailableTime() + rng.genServiceTime(), 
                    server.getQueueSize(), server.getCustomerQueue()), rng); 
            }
            updatedServer = updatedShop.find(s -> s.isSameServer(server)).get(); 
            return Pair.of(updatedShop, new DoneEvent(customer, updatedServer, rng)); 
        }, customer, customer.getArrivalTime() > server.getNextAvailableTime() 
                ? customer.getArrivalTime() 
                : server.getNextAvailableTime(), server); 
    } 

    @Override 
    public String toString() {
        if (server instanceof SelfCheckOut) { 
            return String.format("%.3f", 
                    super.getEventStartTime()) + " " + super.getCustomer() 
                    + " served by self-check " + server.getId(); 
        } else { 
            return String.format("%.3f", 
                    super.getEventStartTime()) + " " + super.getCustomer() 
                    + " served by server " + server.getId(); 
        } 
    } 
} 