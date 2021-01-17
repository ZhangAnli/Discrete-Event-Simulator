package cs2030.simulator; 

/**
 * Leave event class. 
 */
public class LeaveEvent extends Event { 

    /**
     * Leave Event constructor. 
     * When a customer finds no available server and leaves. 
     * @param customer Takes in 1 customer. 
     */
    public LeaveEvent(Customer customer) { 
        super(shop -> { 
            return Pair.of(shop, null);  
        }, customer, customer.getArrivalTime()); 
    } 

    @Override
    public String toString() {
        return String.format("%.3f", super.getCustomer().getArrivalTime()) + 
                " " + super.getCustomer() + " leaves"; 
    }
} 