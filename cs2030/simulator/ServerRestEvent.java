package cs2030.simulator;

/**
 * SERVER_REST event class. 
 */
public class ServerRestEvent extends Event {

    /**
     * Constructor for SERVER_REST event. 
     * @param customer Takes in 1 customer
     * @param server Takes in 1 server
     * @param rng Takes in 1 random generator. 
     */
    public ServerRestEvent(Customer customer, Server server, MyRandomGenerator rng) {
        super(shop -> {
            Server updatedServer = shop.find(c -> c.isSameServer(server)).get();
            Shop updatedShop = shop.replace(new Server(server.getId(), false, 
                    server.getNextAvailableTime() + rng.genRestPeriod(),
                    server.getQueueSize(), server.getCustomerQueue()), rng);
            updatedServer = updatedShop.find(c -> c.isSameServer(server)).get();

            return Pair.of(updatedShop, new ServerBackEvent(customer, updatedServer, rng));
        }, customer, server.getNextAvailableTime(), server);
    }
}