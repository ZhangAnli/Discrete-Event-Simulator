package cs2030.simulator; 

import java.util.function.Function; 

/**
 * Abstract parent Event class. 
 */
public abstract class Event { 

    private final Function<Shop, Pair<Shop, Event>> func; 
    private final Customer customer; 
    private final double eventStartTime; 
    public final Server server; 

    /**
     * Overloaded event constructor. 
     * @param func Takes in a function that returns a shop and a event 
     * @param customer Takes in 1 customer
     * @param eventStartTime Takes in event starting time
     */
    public Event(Function<Shop, Pair<Shop, Event>> func, 
            Customer customer, double eventStartTime) { 
        this.func = func; 
        this.customer = customer; 
        this.eventStartTime = eventStartTime; 
        this.server = null; 
    }

    /**
     * Overloaded event constructor. 
     * @param func Takes in a function that returns a shop and event 
     * @param customer Takes in 1 customer 
     * @param eventStartTime Takes in event starting time 
     * @param server Takes in a server
     */
    public Event(Function<Shop, Pair<Shop, Event>> func, 
            Customer customer, double eventStartTime, Server server) {
        this.func = func;
        this.customer = customer;
        this.eventStartTime = eventStartTime;
        this.server = server;
    }

    // Getter
    public Customer getCustomer() { 
        return customer; 
    }

    public double getEventStartTime() { 
        return this.eventStartTime; 
    }

    public Server getServer() { 
        return this.server; 
    }

    /**
     * Execute function that takes in a shop and returns a new shop. 
     * Each time an execute method is called, it executes the specific event.  
     * @param shop Takes in a shop 
     * @return Applies function on the shop and returns a pair of a shop and event. 
     */
    public final Pair<Shop, Event> execute(Shop shop) {
        return func.apply(shop); 
    }
}