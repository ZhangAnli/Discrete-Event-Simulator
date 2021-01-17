package cs2030.simulator; 

/**
 * SERVER_BACK event class. 
 */
public class ServerBackEvent extends Event {

    /**
     * Constructor for SERVER_BACK event. 
     * @param customer Takes in 1 customer. 
     * @param server Takes in 1 server. 
     * @param rng Takes in 1 random generator. 
     */
    public ServerBackEvent(Customer customer, Server server, MyRandomGenerator rng) { 
        super(shop -> { 
            Shop updatedShop = shop.replace(new Server(server.getId(), true, 
                    server.getNextAvailableTime(), 
                    server.getQueueSize(), server.getCustomerQueue()), rng); 
            Server updatedServer = updatedShop.find(x -> x.isSameServer(server)).get(); 
            return Pair.of(updatedShop, updatedServer.getCustomerQueue().isEmpty()
                    ? null
                    : new ServeEvent(updatedServer.getCustomerQueue().peek(), 
                    updatedServer, rng)); 
        }, customer, server.getNextAvailableTime(), server); 
    }
}