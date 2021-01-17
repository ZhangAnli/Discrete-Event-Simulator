package cs2030.simulator; 

/**
 * Random Generator class that provides access to Random Generator methods. 
 */
public class MyRandomGenerator {

    private final RandomGenerator rng;

    /**
     * Constructor for Random Generator. 
     * @param seed seed value for random generator
     * @param lambda arrival rate for random generator 
     * @param mu service rate for random generator 
     * @param rho rest rate for random generator
     */
    public MyRandomGenerator(int seed, double lambda, double mu, double rho) {
        this.rng = new RandomGenerator(seed, lambda, mu, rho);
    }

    /**
     * Generate interval between arrival times. 
     * @return double 
     */
    public double genInterArrivalTime() {
        return this.rng.genInterArrivalTime();
    }

    /**
     * Generate service time for servers. 
     * @return double
     */
    public double genServiceTime() {
        return this.rng.genServiceTime();
    }

    /**
     * Generate random rest probability. 
     * @return double 
     */
    public double genRandomRest() {
        return this.rng.genRandomRest();
    }

    /**
     * Generate rest period. 
     * @return double 
     */
    public double genRestPeriod() {
        return this.rng.genRestPeriod();
    }

    /**
     * Generate customer type: normal or greedy. 
     * @return double
     */
    public double genCustomerType() {
        return this.rng.genCustomerType();
    }
}