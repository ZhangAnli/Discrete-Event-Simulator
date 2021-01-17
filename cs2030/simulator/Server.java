package cs2030.simulator; 

import java.util.ArrayDeque; 

/**
 * Immutable Server class. 
 */
public class Server { 

    private final int identifier; 
    private final boolean isAvailable; 
    private final boolean hasWaitingCustomer; 
    private final double nextAvailableTime; 
    private final int maxQueue; 
    private final ArrayDeque<Customer> customerQueue; 

    /**
     * Overloaded Server Constructor 1.
     * @param identifier Server ID
     * @param isAvailable Boolean for Server Availability 
     * @param hasWaitingCustomer Boolean for waiting customer 
     * @param nextAvailableTime Next Timing server is available 
     */
    public Server(int identifier, boolean isAvailable, 
            boolean hasWaitingCustomer, double nextAvailableTime) {
        this.identifier = identifier; 
        this.isAvailable = isAvailable; 
        this.hasWaitingCustomer = hasWaitingCustomer; 
        this.nextAvailableTime = nextAvailableTime; 
        this.maxQueue = 1; 
        this.customerQueue = new ArrayDeque<Customer>(); 
    } 

    /**
     * Overloaded Server Constructor 2.
     * @param identifier Server ID 
     * @param isAvailable Boolean for Server Availabilty 
     * @param nextAvailableTime Boolean for waiting customer
     * @param maxQueue Maximum queue length for the server 
     * @param customerQueue Customer Queue waiting for this server 
     */
    public Server(int identifier, boolean isAvailable, double nextAvailableTime,
                int maxQueue, ArrayDeque<Customer> customerQueue) {
        this.identifier = identifier;
        this.isAvailable = isAvailable;
        this.hasWaitingCustomer = false;
        this.nextAvailableTime = nextAvailableTime;
        this.maxQueue = maxQueue;
        this.customerQueue = customerQueue;
    }

    // Getters 
    public int getId() { 
        return this.identifier; 
    } 

    public boolean isAvailable() { 
        return this.isAvailable; 
    } 

    public boolean getHasWaitingCustomer() { 
        return hasWaitingCustomer; 
    } 

    public double getNextAvailableTime() { 
        return nextAvailableTime; 
    } 

    public boolean isSameServer(Server other) { 
        return this.identifier == other.getId(); 
    }

    public ArrayDeque<Customer> getCustomerQueue() { 
        return this.customerQueue; 
    }

    public boolean hasFullQueue() { 
        return this.customerQueue.size() >= this.maxQueue; 
    }

    public int getQueueSize() { 
        return this.maxQueue; 
    }

    public int getCustomerQueueSize() { 
        return this.customerQueue.size(); 
    }

    @Override
    public String toString() { 
        if (isAvailable) { 
            return this.identifier + " is available"; 
        } else if (!hasWaitingCustomer) { 
            return this.identifier + " is busy; available at " + 
                    String.format("%.3f", this.nextAvailableTime); 
        } else { 
            return this.identifier + " is busy; waiting customer to be served at " + 
                    String.format("%.3f", this.nextAvailableTime);  
        } 
    } 
} 