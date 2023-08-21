/** A human within our COVID-19 simulator. Each Human can either be exposed to the virus, infected
 * with the virus or neither */

public class Human {

    /** exposed is true if this Human has been exposed to the virus in the last 7 days but has not
     * been infected yet. exposed is false if this Human has not been exposed to the virus at all
     * in the last 7 days or this Human is currently infected with the virus */
    private boolean exposed;

    /** Number of days after exposure to the virus while still not being infected yet. Must be between
     * 1 and 7, inclusive. If exposed is false, daysExposure will be 0 */
    private int daysExposure;

    /** infected is true if this human is infected with the virus and has not recovered.
     * infected is false if this human is not infected with the virus (human has not been exposed
     * at all or has been exposed but is not infected yet) */
    private boolean infected;

    /** Number of days after infection while still not being recovered yet. Must be between
     * 1 and 10, inclusive (if infected is true). If infected is false, daysInfected must be 0*/
    private int daysInfected;

    /** True if this human has been infected in the past and has recovered. False if this human is still
     * infected or has never been infected before */
    private boolean recovered;

    /** Number of days in which this human has been recovered. If recovered is true, daysRecovered will be
     * equal to or greater than 1 and less than or equal to 280. If recovered is false, daysRecovered will be 0 */
    private int daysRecovered;

    /** The COVID-19 variant that this human has been most recently exposed to or infected with ("Alpha," "Delta,"
     * or "Omicron"). variant will be null if the human has never been exposed or infected */
    private String variant;

    /** Number of humans that this human has had a close contact with in one day. Must be greater than or equal to
     * 0 and less than or equal to 16 **/
    private int encounters;

    /** Helper method to assert the class invariants. Ensure that this object satisfies its class invariants . **/
    private void assertInv() {
        // If this human is exposed, ensure that daysExposure is of an appropriate value as specified above
        // and that this human is not infected. variant should not be null
        if (exposed) {
            assert (daysExposure>=1 && daysExposure<=7);
            assert (!infected && daysInfected==0);
            assert (variant!=null && !variant.isEmpty());
        } else {
            assert (daysExposure==0);
        }
        // If this human is infected, ensure that daysInfected is of an appropriate value as specified above
        // and that this human is not exposed anymore. variant should not be null. recovered should be false.
        if (infected) {
            assert (daysInfected>=1 && daysInfected<=10);
            assert (!exposed && daysExposure==0);
            assert (!recovered && daysRecovered==0);
            assert (variant!=null && !variant.isEmpty());
        } else {
            assert (daysInfected==0);
        }
        // If this human has recovered, ensure that daysRecovered is of an appropriate value. This human
        // should not be infected anymore and variant should not be null
        if (recovered) {
            // This human has been infected in the past and has recovered. This human is not currently infected
            assert (!infected && daysInfected==0);
            assert (daysRecovered>=1 && daysRecovered <= 280);
            assert (variant!=null && !variant.isEmpty());
        } else {
            assert (daysRecovered==0);
        }
        // If this human has never been infected and is currently not exposed, variant will be null
        if (!exposed && !infected && !recovered) {
            assert (variant==null);
        }
        // Every human can have a maximum of 16 close contacts in a day
        assert (encounters>=0 && encounters<=16);
    }

    /** Constructor: creates a new Human. If exposed is true, Human has been exposed for 'daysExposure' days.
     * If infected is true, Human has been infected for 'daysInfected' days. 'variant' will be the COVID-19
     * variant that the Human is exposed to or infected with (can be null if Human is neither exposed or infected) **/
    public Human(boolean exposed, int daysExposure, boolean infected, int daysInfected, String variant) {

        // Check preconditions
        if (exposed) {
            assert (daysExposure>=1 && daysExposure<=7);
            assert (!infected && daysInfected==0);
            assert (variant!=null && !variant.isEmpty());
        } else {
            assert (daysExposure==0);
        }
        if (infected) {
            assert (daysInfected>=1 && daysInfected<=10);
            assert (!exposed && daysExposure==0);
            assert (!recovered && daysRecovered==0);
            assert (variant!=null && !variant.isEmpty());
        } else {
            assert (daysInfected==0);
        }
        if (!exposed && !infected) {
            assert (variant==null);
        }

        // Assign parameters
        this.exposed = exposed;
        this.daysExposure = daysExposure;
        this.infected = infected;
        this.daysInfected = daysInfected;
        this.variant = variant;
        // All humans have never had the virus at the beginning of the simulation
        recovered = false;
        daysRecovered = 0;
        encounters = 0;

        // Re-assert the class invariants
        assertInv();

    }

    /** Returns if this human is exposed or not **/
    public boolean isExposed() {
        return exposed;
    }

    /** Returns the number of days since this human was exposed **/
    public int getDaysExposure() {
        return daysExposure;
    }

    /** Returns if this human is infected or not **/
    public boolean isInfected() {
        return infected;
    }

    /** Returns the number of days this human has been infected **/
    public int getDaysInfected() {
        return daysInfected;
    }

    /** Returns if this human has ever recovered from the virus or not **/
    public boolean isRecovered() {
        return recovered;
    }

    /** Returns the number of days since this human last recovered **/
    public int getDaysRecovered() {
        return daysRecovered;
    }

    /** Returns the variant that this human is currently exposed to or currently infected with or has
     * recovered from. Null if this human is not exposed and has never been infected */
    public String getVariant() { return variant; }

    /** Returns the number of other humans that this human has had a close contact with in a day **/
    public int getEncounters() { return encounters; }

    /** This Human comes into contact with Human 'h'. Either human can be exposed or infected or neither.
     * A Human that is not exposed and not infected can be someone who has recovered in the past or
     * has never been infected with the virus at all. This method determines the health status
     * of each Human right after coming into contact with one another */
    public void contact(Human h) {
        // Check preconditions
        assert (h!=null);
        assertInv();
        h.assertInv();

        if ((!exposed && !infected && h.infected) || (!h.exposed && !h.infected && infected)) {
            // A non-exposed non-infected Human comes into contact with an infected Human.
            // Non-exposed non-infected Human becomes exposed.
            nonMeetsInfected(h);
        } else if ((infected && h.exposed) || (exposed && h.infected)) {
            // An exposed Human comes into contact with an infected Human. Exposed Human
            // gets re-exposed and restarts incubation period.
            exposedMeetsInfected(h);
        }

        // Update number of Humans encountered in a day
        encounters += 1;
        h.encounters += 1;
        // Assert class invariants
        assertInv();
        h.assertInv();
    }

    /** A non-exposed, non-infected Human meets an infected Human.
     * Non-exposed non-infected Human becomes exposed **/
    public void nonMeetsInfected(Human h) {
        // Check preconditions
        assert (h!=null);
        assertInv();
        h.assertInv();

        // Either this Human or Human 'h' must be non-exposed non-infected while the other one must be infected.
        assert ((!exposed && !infected && h.infected) || (!h.exposed && !h.infected && infected));

        if (infected) {
            // this Human is infected while Human 'h' is non-exposed non-infected.
            // Human 'h' becomes exposed.
            h.exposed = true;
            h.daysExposure = 1;
            h.variant = variant;
        } else {
            // Human 'h' is infected while this Human is non-exposed non-infected.
            // this Human becomes exposed
            exposed = true;
            daysExposure = 1;
            variant = h.variant;
        }

        // Assert class invariants
        assertInv();
        h.assertInv();
    }

    /** An exposed Human meets an infected Human.
     * Restarts the incubation period for the exposed Human **/
    public void exposedMeetsInfected(Human h) {
        // Check preconditions
        assert (h!=null);
        assertInv();
        h.assertInv();

        // Either one of this Human or Human 'h' must be exposed and the other one must be infected.
        assert ((infected && h.exposed) || (exposed && h.infected));

        // Restarts the count for daysExposure
        // Whoever is exposed gets re-exposed and must go through another incubation period again.
        if (h.exposed) {
            h.daysExposure = 1;
            h.variant = variant;
        } else {
            daysExposure = 1;
            variant = h.variant;
        }

        // Assert class invariants
        assertInv();
        h.assertInv();
    }

    /** Updates the day in the simulation. Simulation changes to a different day and determines
     * the health status of this Human after 1 day **/
    public void updateDay() {

        assertInv();

        if (!exposed && !infected) {
            // This Human is currently non-exposed and non-infected. Human may or may not have been infected in the past.
            if (recovered) {
                // This Human has been infected in the past. daysRecovered increments by 1.
                daysRecovered += 1;
            }
        } else if (exposed) {
            // Call helper function for exposed Human
            updateExposedDay();
        } else if (infected) {
            // Call helper function for infected Human
            updateInfectedDay();
        }

        // Reset number of humans encountered in a day
        encounters = 0;
        // Assert class invariant
        assertInv();

    }

    /** Updates the day in the simulation for an exposed human. Simulations changes to a different day and
     * determines the health status of an exposed human after 1 day. Exposed human either remains exposed and
     * continues with their incubation period, ends their incubation period and does not get infected or gets infected */
    public void updateExposedDay() {

        // Check precondition
        assert (exposed && daysExposure >=1);
        assertInv();

        // Risk of infection (turning infected from exposed)
        double infectionRisk = Math.random();

        if (!recovered) {
            // This human has never been infected before
            if (variant.equals("Alpha")) {
                // Alpha variant has a secondary attack rate of 36.4% and an average incubation period of 4.96 days
                if (daysExposure>=4.96 && infectionRisk<=0.364) {
                    // Human turns infected
                    infected = true;
                    daysInfected = 1;
                    exposed = false;
                    daysExposure = 0;
                    recovered = false;
                    daysRecovered = 0;
                } else {
                    if (daysExposure<7) {
                        // Proceed with the incubation period
                        daysExposure += 1;
                    } else if (daysExposure==7) {
                        // Incubation period is over, human is no longer exposed
                        exposed = false;
                        daysExposure = 0;
                        variant = null;
                    }
                }
            } else if (variant.equals("Delta")) {
                // Delta variant has a secondary attack rate of 58.2% and an average incubation period of 4.43 days
                if (daysExposure>=4.43 && infectionRisk<=0.582) {
                    infected = true;
                    daysInfected = 1;
                    exposed = false;
                    daysExposure = 0;
                    recovered = false;
                    daysRecovered = 0;
                } else {
                    if (daysExposure<7) {
                        // Proceed with the incubation period
                        daysExposure += 1;
                    } else if (daysExposure==7) {
                        // Incubation period is over, human is no longer exposed
                        exposed = false;
                        daysExposure = 0;
                        variant = null;
                    }
                }
            } else if (variant.equals("Omicron")) {
                // Omicron variant has a secondary attack rate of 80.9% and an average incubation period of 3.61 days
                if (daysExposure>=3.61 && infectionRisk<=0.809) {
                    infected = true;
                    daysInfected = 1;
                    exposed = false;
                    daysExposure = 0;
                    recovered = false;
                    daysRecovered = 0;
                } else {
                    if (daysExposure<7) {
                        // Proceed with the incubation period
                        daysExposure += 1;
                    } else if (daysExposure==7) {
                        // Incubation period is over, human is healthy
                        exposed = false;
                        daysExposure = 0;
                        variant = null;
                    }
                }
            }
        } else if (recovered) {
            // This human has been infected in the past and thus has higher immunity and protection
            // to the variant they were infected with.
            if (variant.equals("Alpha")) {
                // Alpha variant has a secondary attack rate of 9.8% for reinfection
                // Same average incubation period of 4.96 days as before (can be compared to 5 since we use int)
                if (daysExposure>= 5 && infectionRisk<=0.098) {
                    // Human turns infected
                    infected = true;
                    daysInfected = 1;
                    exposed = false;
                    daysExposure = 0;
                    recovered = false;
                    daysRecovered = 0;
                } else {
                    if (daysExposure<7) {
                        // Proceed with the incubation period
                        daysExposure += 1;
                        daysRecovered += 1;
                    } else if (daysExposure==7) {
                        // Incubation period is over, human is healthy
                        exposed = false;
                        daysExposure = 0;
                        daysRecovered += 1;
                        variant = null;
                    }
                }
            } else if (variant.equals("Delta")) {
                // Delta variant has a secondary attack rate of 8% for reinfection
                // Same average incubation period of 4.43 days as before (can be compared to 5 since we use int)
                if (daysExposure>= 5 && infectionRisk<=0.08) {
                    // Human turns infected
                    infected = true;
                    daysInfected = 1;
                    exposed = false;
                    daysExposure = 0;
                    recovered = false;
                    daysRecovered = 0;
                } else {
                    if (daysExposure<7) {
                        // Proceed with the incubation period
                        daysExposure += 1;
                        daysRecovered += 1;
                    } else if (daysExposure==7) {
                        // Incubation period is over, Human is healthy
                        exposed = false;
                        daysExposure = 0;
                        daysRecovered += 1;
                        variant = null;
                    }
                }
            } else if (variant.equals("Omicron")) {
                // Omicron variant has a secondary attack rate of 44% for reinfection
                // Same average incubation period of 3.61 days as before
                if (daysExposure>=3.61 && infectionRisk<=0.44) {
                    infected = true;
                    daysInfected = 1;
                    exposed = false;
                    daysExposure = 0;
                    recovered = false;
                    daysRecovered = 0;
                } else {
                    if (daysExposure<7) {
                        // Proceed with the incubation period
                        daysExposure += 1;
                        daysRecovered += 1;
                    } else if (daysExposure==7) {
                        // Incubation period is over, Human is healthy
                        exposed = false;
                        daysExposure = 0;
                        daysRecovered += 1;
                        variant = null;
                    }
                }
            }
        }

        // Assert class invariants
        assertInv();

    }

    /** Updates the status of an infected Human after each day. Determines if an infected Human
     * recovers or not. Assumes that all infected humans only recovers after the 10th day of infection */
    public void updateInfectedDay() {

        // Check precondition
        assert (infected);
        assertInv();

        if (daysInfected<10) {
            // Human will still be sick and will not recover yet
            daysInfected += 1;
        } else {
            // Human is on the 10th day of being sick and will now recover
            infected = false;
            recovered = true;
            daysInfected = 0;
            daysRecovered = 1;
        }

        // Assert class invariant
        assertInv();
    }


}
