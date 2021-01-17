package cs2030.simulator; 

import java.util.Comparator; 

/** 
* Comparator class to compare 2 events using event start time and customer ID. 
* @return Comparator
*/ 
public class EventComparator implements Comparator<Event> {
     
    /**
     * Overriding compare method that compares two events. 
     * Compares event start time first, then breaks ties using customer ID. 
     * @return int 
     */
    @Override 
    public int compare(Event event1, Event event2) { 
        if (event1.getEventStartTime() > event2.getEventStartTime()) { 
            return 1; 
        } else if (event1.getEventStartTime() < event2.getEventStartTime()) { 
            return -1; 
        } else { 
            int customer1 = event1.getCustomer().getId(); 
            int customer2 = event2.getCustomer().getId(); 
            return Integer.compare(customer1, customer2);   
        } 
    } 
} 