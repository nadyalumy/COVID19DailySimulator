/** An instance of a COVID-19 simulator that simulates the spread of COVID-19 in a community over a given
 * period of days **/

public class Simulator {

    /** The main program for a full simulation of COVID-19 spread in a community over a given period of days.
     * Run this main program to start the simulation and see the results.
     * This main program accepts 8 integer arguments in the following sequence: number of humans exposed to
     * the Alpha variant, the number of humans exposed to the Delta variant, the number of humans exposed
     * to the Omicron variant, the number of humans infected by the Alpha variant, the number of humans
     * infected by the Delta variant, the number of humans infected by the Omicron variant, the number
     * of humans that are non-exposed and non-infected to any variants and lastly, the number of days
     * that the simulation should simulate over**/
    public static void main(String[] args) {

        // Check the number of input arguments given
        if (args.length!=8) {
            throw new IllegalArgumentException("Incorrect number of arguments. Please enter 8 numbers corresponding to \n"
                    + " the number of humans exposed to the Alpha variant, the number of humans exposed to the Delta \n"
                    + " variant, the number of humans exposed to the Omicron variant, the number of humans infected by the \n"
                    + " Alpha variant, the number of humans infected by the Delta variant, the number of humans infected \n"
                    + " by the Omicron variant, the number of humans that are non-exposed and non-infected to any variants \n"
                    + " and lastly, the number of days that the simulation should simulate over");
        }

        // Convert input arguments into integers
        int numExposedAlpha = Integer.parseInt(args[0]);
        int numExposedDelta = Integer.parseInt(args[1]);
        int numExposedOmicron = Integer.parseInt(args[2]);
        int numInfectedAlpha = Integer.parseInt(args[3]);
        int numInfectedDelta = Integer.parseInt(args[4]);
        int numInfectedOmicron = Integer.parseInt(args[5]);
        int numHealthy = Integer.parseInt(args[6]);
        int totalDays = Integer.parseInt(args[7]);

        // Create an instance of a community
        Community comm = new Community(numExposedAlpha, numExposedDelta, numExposedOmicron, numInfectedAlpha,
                numInfectedDelta, numInfectedOmicron, numHealthy);

        // Print initial state of community
        String initialState = String.format("Total population is %d", comm.getPopulation());
        String dayZero = String.format("Day 0: %d infected, %d exposed, %d recovered", comm.getNumInfected(),
                comm.getNumExposed(), comm.getNumRecovered());
        System.out.println(initialState);
        System.out.println(dayZero);

        // Run the simulation over the given period of days. Print the state of the community after each day.
        for (int i=1; i<=totalDays; ++i) {
            comm.communityDaily();
            // Print new state of community after one day
            String currDay = String.format("Day %d: %d infected, %d exposed, %d recovered", i,
                    comm.getNumInfected(), comm.getNumExposed(), comm.getNumRecovered());
            System.out.println(currDay);
            // Update to a new day
            comm.communityUpdateDay();
        }

    }

}
