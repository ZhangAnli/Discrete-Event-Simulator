package cs2030.simulator; 

import java.util.List; 
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Optional; 
import java.util.function.Predicate;
import java.util.ArrayDeque;

/**
 * Immutable Shop Class. 
 */
public class Shop { 

    private final List<Server> listOfServers; 
    private final MyRandomGenerator rng; 

    /**
     * Overloaded constructor for Shop class. 
     * @param n Takes in an integer n to generate n servers. 
     */
    public Shop(int n) { 
        this.listOfServers = Stream.iterate(1, x -> x + 1)
                .limit(n)
                .map(x -> new Server(x, true, false, 0.000))
                .collect(Collectors.toList()); 
        this.rng = null; 
    }

    /**
     * Overloaded constructor for Shop class. 
     * @param n Takes in an integer n to generate n servers
     * @param queueSize Takes in the maximum queuu size for servers
     * @param rng Takes in a random generator
     */
    public Shop(int n, int queueSize, MyRandomGenerator rng) {
        this.listOfServers = Stream.iterate(1, x -> x + 1)
                .map(x -> new Server(x, true, 0, queueSize, new ArrayDeque<Customer>()))
                .limit(n)
                .collect(Collectors.toList());
        this.rng = rng;
    }

    /**
     * Overloaded constructor for Shop class.
     * @param list Takes in a list of servers 
     */
    public Shop(List<Server> list) { 
        this.listOfServers = list; 
        this.rng = null; 
    }

    /**
     * Overloaded constructor for Shop class.
     * @param serverList Takes in a list of servers 
     * @param rng Takes in a random generator 
     */
    public Shop(List<Server> serverList, MyRandomGenerator rng) {
        this.listOfServers = serverList;
        this.rng = rng;
    }

    /**
     * Find method to return the first server that satisfies predicate.
     * @param pred Takes in a predicate 
     * @return Optional of Server. 
     */
    public Optional<Server> find(Predicate<Server> pred) { 
        return listOfServers.stream()
                .filter(pred)
                .findFirst(); 
    }

    /**
     * Replace method to replace the server by ID. 
     * @param server Takes in 1 server 
     * @return Updated shop 
     */
    public Shop replace(Server server) {
        List<Server> newList = this.listOfServers.stream()
                .map(x -> x.getId() != server.getId() ? x : server)
                .collect(Collectors.toList());
        return new Shop(newList); 
    }

    /**
     * Replace method to replace the server by ID. 
     * @param server Takes in 1 server 
     * @param rng Takes in 1 random generator 
     * @return Updated shop
     */
    public Shop replace(Server server, MyRandomGenerator rng) {
        List<Server> newList = this.listOfServers.stream()
                .map(x -> x.getId() != server.getId() ? x : server)
                .collect(Collectors.toList());
        return new Shop(newList, rng); 
    }

    // GETTERS 
    public MyRandomGenerator getRNG() {
        return this.rng;
    }

    public List<Server> getServerList() { 
        return this.listOfServers; 
    }

    @Override
    public String toString() { 
        return listOfServers.toString(); 
    }
}