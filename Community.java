import java.util.Random;

/** A community of humans within our COVID-19 simulator. A community can have a mixture of
 * humans exposed to COVID-19, humans infected with COVID-19 or humans that are neither exposed nor infected. */
public class Community {

    /** An array storing all the humans in this community **/
    private Human[] humans;

    /** Number of humans in this community **/
    private int population;

    /** Number of humans currently exposed to COVID-19 in this community */
    private int numExposed;

    /** Number of humans currently infected with COVID-19 in this community */
    private int numInfected;

    /** Number of non-exposed non-infected humans currently in this community **/
    private int numHealthy;

    /** Number of non-exposed non-infected humans who have recently recovered from the virus currently in this community **/
    private int numRecovered;

    /** Helper method to assert the class invariants. Ensure that this object satisfies its class invariants . **/
    private void assertInv() {

        // population values cannot be negative
        assert (population>=0);
        assert (numExposed>=0);
        assert (numInfected>=0);
        assert (numHealthy>=0);
        assert (numRecovered>=0);

        // length of humans array must be equal to the population value
        assert (humans.length==population);
        // population value must be equal to the total number of exposed, infected and non-exposed non-infected humans
        assert (population==numExposed+numInfected+numHealthy);
        // all recovered humans are a part of non-exposed non-infected humans
        assert (numRecovered <= numHealthy);

    }

    /** Constructor: creates a new Community that starts with 'numExposedAlpha' humans exposed to the Alpha variant,
     * 'numExposedDelta' humans exposed to the Delta variant, 'numExposedOmicron' humans exposed to the Omicron variant,
     * 'numInfectedAlpha' humans infected with the Alpha variant, 'numInfectedDelta' humans infected with the Delta variant,
     * 'numInfectedOmicron' humans infected with the Omicron variant, and 'numHealthy' humans that are non-exposed non-infected. **/
    public Community(int numExposedAlpha, int numExposedDelta, int numExposedOmicron, int numInfectedAlpha,
            int numInfectedDelta, int numInfectedOmicron, int numHealthy) {

        // Check preconditions (arguments cannot be negative values)
        assert (numExposedAlpha>=0);
        assert (numExposedDelta>=0);
        assert (numExposedOmicron>=0);
        assert (numInfectedAlpha>=0);
        assert (numInfectedDelta>=0);
        assert (numInfectedOmicron>=0);
        assert (numHealthy>=0);

        // Assign fields
        this.numExposed = numExposedAlpha + numExposedDelta + numExposedOmicron;
        this.numInfected = numInfectedAlpha + numInfectedDelta + numInfectedOmicron;
        this.numHealthy = numHealthy;
        numRecovered = 0;
        population = this.numExposed + this.numInfected + this.numHealthy;

        // Create an array to store the humans in the community
        humans = new Human[population];

        for (int i=0; i<numExposedAlpha; ++i) {
            // store humans exposed to the Alpha variant
            humans[i] = new Human(true, 1, false, 0, "Alpha");
        }
        for (int j=numExposedAlpha; j<numExposedAlpha+numExposedDelta; ++j) {
            // store humans exposed to the Delta variant
            humans[j] = new Human(true, 1, false, 0, "Delta");
        }
        for (int k=numExposedAlpha+numExposedDelta; k<numExposed; ++k) {
            // store humans exposed to the Omicron variant
            humans[k] = new Human(true, 1, false, 0, "Omicron");
        }
        for (int i=numExposed; i<numExposed+numInfectedAlpha; ++i) {
            // store humans infected with the Alpha variant
            humans[i] = new Human(false, 0, true, 1, "Alpha");
        }
        for (int j=numExposed+numInfectedAlpha; j<numExposed+numInfectedAlpha+numInfectedDelta; ++j) {
            // store humans infected with the Delta variant
            humans[j] = new Human(false, 0, true, 1, "Delta");
        }
        for (int k=numExposed+numInfectedAlpha+numInfectedDelta; k<numExposed+numInfected; ++k) {
            // store humans infected with the Omicron variant
            humans[k] = new Human(false, 0, true, 1, "Omicron");
        }
        for (int i=numExposed+numInfected; i<population; ++i) {
            // store healthy humans
            humans[i] = new Human(false, 0, false, 0, null);
        }

        // Shuffle the 'humans' array and make sure all humans are positioned at random indexes
        // Use the helper method "shuffleHumans"
        shuffleHumans();

        // Assert class invariants
        assertInv();

    }

    /** Returns the total number of humans in the community **/
    public int getPopulation() { return population; }

    /** Returns the total number of humans currently exposed to COVID-19 in the community **/
    public int getNumExposed() { return numExposed; }

    /** Returns the total number of humans currently infected with COVID-19 in the community **/
    public int getNumInfected() { return numInfected; }

    /** Returns the total number of non-exposed non-infected humans currently in the community **/
    public int getNumHealthy() { return numHealthy; }

    /** Returns the total number of humans who are currently recovered from COVID-19 in the community **/
    public int getNumRecovered() { return numRecovered; }

    /** This method shuffles the array of Human objects in "humanArray" using the Fisher-Yates
     * shuffling algorithm. Ensures that exposed, infected and non-exposed non-infected humans are
     * positioned randomly in the array to model the random distribution of physical positions of
     * humans in a community */
    public void shuffleHumans() {

        // Check precondition
        assertInv();

        Random rand = new Random();
        // Traverse the 'humans' array from the end and swap elements randomly
        for (int i= humans.length-1; i>0; --i) {
            // Generate a random index number between 0 and i
            int idx = rand.nextInt(i+1);
            // Swap current human with the human at the generated index
            Human tmp = humans[i];
            humans[i] = humans[idx];
            humans[idx] = tmp;
        }

        // Reassert class invariant
        assertInv();

    }

    /** Creates a simulation of one single day in which each human in the community encounters 16
     * other humans throughout the day. Updates the status of each human and the total number of
     * exposed humans, infected humans, and non-exposed non-infected humans each day in the community */
    public void communityDaily() {

        // Check precondition
        assertInv();

        // Traverse through the array of humans to make each human encounter other humans in the array
        for (int i=0; i<population-1; ++i) {
            int j = i+1;
            while (humans[i].getEncounters()<16 && j<population) {
                // Assume that each human has a 0.5 probability of coming into contact with another human in the community
                double contactProb = Math.random();
                if (contactProb>=0.5) {
                    // humans[i] and humans[j] are in close contact
                    humans[i].contact(humans[j]);
                }
                j += 1;
            }
        }

        // Now that all humans have interacted with other humans, traverse through the array again to
        // calculate the new number of non-exposed non-infected, exposed and infected humans in the community
        // at the end of 1 day
        numExposed = 0;
        numInfected = 0;
        numHealthy = 0;
        numRecovered = 0;
        for (int j=0; j<population; ++j) {
            if (humans[j].isExposed()) {
                numExposed += 1;
            } else if (humans[j].isInfected()) {
                numInfected += 1;
            }
            if (humans[j].isRecovered()) {
                numRecovered += 1;
            }
        }

        // Reassert class invariants
        assertInv();

    }

    /** Updates the day in the simulation to the next day. This method updates the health status of
     * all humans in the simulation after 1 day. Uses the 'updateDay()' method from the Human class. */
    public void communityUpdateDay() {
        // Check precondition
        assertInv();

        for (int i=0; i<population; ++i) {
            humans[i].updateDay();
        }

        // Reassert class invariants
        assertInv();
    }


}
