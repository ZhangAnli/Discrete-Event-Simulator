package cs2030.simulator; 

/**
 * Immutable Customer class with ID, arrival time and boolean greedy. 
 */
public class Customer { 

    private final int id; 
    private final double arrivalTime; 
    private final boolean greedy; 

    /**
     * Overloaded customer constructor. 
     * @param id Customer ID
     * @param arrivalTime Customer Arrival Time
     */
    public Customer(int id, double arrivalTime) { 
        this.id = id; 
        this.arrivalTime = arrivalTime; 
        this.greedy = false; 
    } 

    /**
     * Overloaded customer constructor. 
     * @param id Customer ID 
     * @param arrivalTime Customer Arrival Time
     * @param greedy Boolean for whether customer is greedy 
     */
    public Customer(int id, double arrivalTime, boolean greedy) { 
        this.id = id; 
        this.arrivalTime = arrivalTime; 
        this.greedy = greedy; 
    } 

    // Getters 
    public int getId() { 
        return this.id; 
    } 

    public double getArrivalTime() { 
        return this.arrivalTime; 
    } 

    public boolean isGreedy() { 
        return this.greedy; 
    }

    @Override
    public String toString() { 
        if (greedy) { 
            return this.id + "(greedy)"; 
        } else { 
            return "" + this.id; 
        }
    } 
} 