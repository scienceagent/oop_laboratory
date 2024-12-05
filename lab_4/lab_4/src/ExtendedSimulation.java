

import java.util.Random;

public class ExtendedSimulation {
    private Ecosystem ecosystem;
    private Random random;
    private int randomEventProbability; // Probability for random events

    public ExtendedSimulation(Ecosystem ecosystem) {
        this.ecosystem = ecosystem;
        this.random = new Random();
        this.randomEventProbability = 10; // Example: 10% chance for a random event
    }

    public void run(int steps) {
        for (int i = 0; i < steps; i++) {
            System.out.println("\n--- Simulation Step " + (i + 1) + " ---");
            ecosystem.simulateStep();

            // Introducing random events with a certain probability
            if (random.nextInt(100) < randomEventProbability) {
                ecosystem.triggerRandomEvent();
            }

            // Display the current state of the ecosystem
            ecosystem.displayMatrix();
        }
        ecosystem.generateFinalReport();
    }
}