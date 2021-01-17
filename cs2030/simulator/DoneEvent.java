package cs2030.simulator; 

/**
 * Done event class. 
 */
public class DoneEvent extends Event { 

    /**
     * Overloaded constructor for Done Event.
     * Occurs when customer is done being served. 
     * @param customer Takes in 1 customer
     * @param server Takes in 1 server 
     */
    public DoneEvent(Customer customer, Server server) {         
        super(shop -> { 
            Shop updatedShop = shop.replace(new Server(server.getId(), 
                    true, server.getNextAvailableTime(), 
                    server.getQueueSize(), server.getCustomerQueue())); 
            return Pair.of(updatedShop, null); 
        }, customer, server.getNextAvailableTime(), server); 
    }

    /**
     * Overloaded constructor for Done Event.
     * Occurs when customer is done being served. 
     * @param customer Takes in 1 customer
     * @param server Takes in 1 server
     * @param rng Takes in 1 random generator 
     */
    public DoneEvent(Customer customer, Server server, MyRandomGenerator rng) {
        super(shop -> {
            Shop updatedShop; 
            if (server instanceof SelfCheckOut) { 
                updatedShop = shop.replace(new SelfCheckOut(server.getId(), 
                        true, server.getNextAvailableTime(),
                        server.getQueueSize()),rng);
                return Pair.of(updatedShop, null);
            } else { 
                updatedShop = shop.replace(new Server(server.getId(), 
                        true, server.getNextAvailableTime(),
                        server.getQueueSize(), server.getCustomerQueue()),rng);
                return Pair.of(updatedShop, null);
            }
        }, customer, server.getNextAvailableTime(), server);
    }

    @Override 
    public String toString() {
        if (server instanceof SelfCheckOut) { 
            return String.format("%.3f", super.getEventStartTime()) + 
                    " " + super.getCustomer() + 
                    " done serving by self-check " + server.getId(); 
        } else { 
            return String.format("%.3f", super.getEventStartTime()) + 
                    " " + super.getCustomer() + 
                    " done serving by server " + server.getId(); 
        } 
    } 
} 