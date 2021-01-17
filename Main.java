import cs2030.simulator.Customer;
import cs2030.simulator.Server;
import cs2030.simulator.Event;
import cs2030.simulator.EventComparator;
import cs2030.simulator.ArriveEvent; 
import cs2030.simulator.DoneEvent; 
import cs2030.simulator.LeaveEvent; 
import cs2030.simulator.ServeEvent; 
import cs2030.simulator.WaitEvent; 
import cs2030.simulator.Pair; 
import cs2030.simulator.MyRandomGenerator;
import cs2030.simulator.Shop;
import cs2030.simulator.ServerRestEvent;
import cs2030.simulator.ServerBackEvent;
import cs2030.simulator.SelfCheckOut;
import java.util.PriorityQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Main {
    /**
     * This is a Discrete Event Simulator. This is the main method to get user input.
     * @param args Below are the list of args for user to input 
     - seed: seed for random generator. 
     - numberOfCustomers: number of customers to be simulated. 
     - numberOfSelfCheckOut: number of Self-Check Counters. 
     - queueMax: maximum customers waiting in a queue.
     - arrivalRate: arrival rate for random generator.
     - serviceRate: service rate for random generator.
     - restRate: resting rate for random generator.
     - restProb: resting probability for random generator.
     - greedyProbability: probability of customer being greedy.
     */
    public static void main(String[] args) {

        int seed = 0; 
        int numberOfCustomers = 0; 
        int numberOfServers = 0; 
        int numberOfSelfCheckOut = 0; 
        int queueMax = 1; 
        double arrivalRate = 0; 
        double serviceRate = 0; 
        double restRate = 0; 
        double restProb = 0; 
        double greedyProbability = 0; 

        // INITIALISE INPUT VALUE 
        if (args.length == 5) {
            seed = Integer.parseInt(args[0]); 
            numberOfServers = Integer.parseInt(args[1]); 
            numberOfCustomers = Integer.parseInt(args[2]);
            arrivalRate = Double.parseDouble(args[3]); 
            serviceRate = Double.parseDouble(args[4]); 
        } else if (args.length == 6) { 
            seed = Integer.parseInt(args[0]); 
            numberOfServers = Integer.parseInt(args[1]); 
            queueMax = Integer.parseInt(args[2]);
            numberOfCustomers = Integer.parseInt(args[3]);
            arrivalRate = Double.parseDouble(args[4]); 
            serviceRate = Double.parseDouble(args[5]); 
        } else if (args.length == 8) { 
            seed = Integer.parseInt(args[0]); 
            numberOfServers = Integer.parseInt(args[1]); 
            queueMax = Integer.parseInt(args[2]);
            numberOfCustomers = Integer.parseInt(args[3]);
            arrivalRate = Double.parseDouble(args[4]); 
            serviceRate = Double.parseDouble(args[5]);
            restRate = Double.parseDouble(args[6]);
            restProb = Double.parseDouble(args[7]);
        } else if (args.length == 9) { 
            seed = Integer.parseInt(args[0]); 
            numberOfServers = Integer.parseInt(args[1]); 
            numberOfSelfCheckOut = Integer.parseInt(args[2]); 
            queueMax = Integer.parseInt(args[3]);
            numberOfCustomers = Integer.parseInt(args[4]);
            arrivalRate = Double.parseDouble(args[5]); 
            serviceRate = Double.parseDouble(args[6]);
            restRate = Double.parseDouble(args[7]);
            restProb = Double.parseDouble(args[8]);
        } else if (args.length == 10) { 
            seed = Integer.parseInt(args[0]); 
            numberOfServers = Integer.parseInt(args[1]); 
            numberOfSelfCheckOut = Integer.parseInt(args[2]); 
            queueMax = Integer.parseInt(args[3]);
            numberOfCustomers = Integer.parseInt(args[4]);
            arrivalRate = Double.parseDouble(args[5]); 
            serviceRate = Double.parseDouble(args[6]);
            restRate = Double.parseDouble(args[7]);
            restProb = Double.parseDouble(args[8]);
            greedyProbability = Double.parseDouble(args[9]);
        }

        MyRandomGenerator rng = new MyRandomGenerator(seed, arrivalRate, serviceRate, restRate); 
        PriorityQueue<Event> pq = new PriorityQueue<>(new EventComparator()); 
        double totalWaitingTime = 0; 
        double averageWaitingTime = 0; 
        int customersServed = 0; 
        int customersLeft = 0; 

        double arriveTime = 0.0; 
        for (int i = 0; i < numberOfCustomers; i++) { 
            if (rng.genCustomerType() < greedyProbability) { 
                pq.add(new ArriveEvent(new Customer(i + 1, arriveTime, true), rng)); 
            } else { 
                pq.add(new ArriveEvent(new Customer(i + 1, arriveTime, false), rng)); 
            }
            arriveTime += rng.genInterArrivalTime(); 
        }

        ArrayList<Server> listOfServers = new ArrayList<Server>(); 
        for (int i = 0; i < numberOfServers + numberOfSelfCheckOut; i++) { 
            if (i < numberOfServers) {
                listOfServers.add(new Server(i + 1, true, 0, queueMax, new ArrayDeque<Customer>()));
            } else { 
                listOfServers.add(new SelfCheckOut(i + 1, true, 0, queueMax)); 
            }
        }
        
        Shop shop = new Shop(listOfServers, rng); 
    
        while (!pq.isEmpty()) { 
            Event event = pq.poll(); 
            // System.out.println(shop);
            if (event instanceof ArriveEvent) { 
                System.out.println(event); 
                Pair<Shop,Event> pair = event.execute(shop); 
                shop = pair.first(); 
                pq.add(pair.second()); 
            } else if (event instanceof ServeEvent) { 
                System.out.println(event); 
                Pair<Shop,Event> pair = event.execute(shop); 
                shop = pair.first(); 
                pq.add(pair.second()); 
                totalWaitingTime += (event.getEventStartTime() 
                        - event.getCustomer().getArrivalTime());
            } else if (event instanceof WaitEvent) { 
                System.out.println(event); 
                Pair<Shop,Event> pair = event.execute(shop); 
                shop = pair.first(); 
            } else if (event instanceof DoneEvent) { 
                System.out.println(event); 
                Pair<Shop,Event> pair = event.execute(shop); 
                shop = pair.first(); 
                Server server = shop.find(x -> x.isSameServer(event.getServer())).get(); 
                if (server instanceof SelfCheckOut) { 
                    if (!(server.getCustomerQueue().isEmpty())) {
                        pq.add(new ServeEvent(server.getCustomerQueue().peek(), server, rng));
                    }
                } else { 
                    if (shop.getRNG().genRandomRest() < restProb) { 
                        pq.add(new ServerRestEvent(event.getCustomer(), server, rng)); 
                    } else if (!(server.getCustomerQueue().isEmpty())) {
                        pq.add(new ServeEvent(server.getCustomerQueue().peek(), server, rng));
                    } 
                }
                customersServed++; 
            } else if (event instanceof LeaveEvent) { 
                System.out.println(event); 
                customersLeft++; 
            } else if (event instanceof ServerRestEvent) { 
                Pair<Shop,Event> pair = event.execute(shop); 
                shop = pair.first(); 
                pq.add(pair.second()); 
            } else if (event instanceof ServerBackEvent) { 
                Pair<Shop,Event> pair = event.execute(shop); 
                shop = pair.first(); 
                if (pair.second() != null) { 
                    pq.add(pair.second()); 
                } 
            }
        }

        if (totalWaitingTime != 0) { 
            averageWaitingTime = (double) totalWaitingTime / customersServed; 
        } 

        System.out.println("[" + String.format("%.3f", averageWaitingTime) + 
                " " + customersServed + " " + customersLeft + "]"); 
    } 
}