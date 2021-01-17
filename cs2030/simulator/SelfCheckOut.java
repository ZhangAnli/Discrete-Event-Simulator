package cs2030.simulator; 

import java.util.ArrayDeque;

/**
 * Self Check Out Counter class that extends Server. 
 */
public class SelfCheckOut extends Server {

    private static final ArrayDeque<Customer> queueList = new ArrayDeque<Customer>(); 

    /**
     * Constructor for Self-Checkout servers. 
     * Self-check servers share the same queue 
     * @param id Self-checkout Identifier 
     * @param isAvailable Server availability 
     * @param nextAvailableTime Server next available time. 
     * @param queueSize Server maximum queue size
     */
    public SelfCheckOut(int id, boolean isAvailable, double nextAvailableTime, int queueSize) { 
        super(id, isAvailable, nextAvailableTime, queueSize, queueList); 
    }
}
