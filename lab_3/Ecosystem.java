package lab_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Ecosystem {
    private List<EcosystemEntity> entities = new ArrayList<>();
    private final int gridSize = 10;
    private final Random random = new Random();
    private final double randomEventProbability = 0.2;
    private EcosystemReport report = new EcosystemReport();

    // Method to access the report
    public EcosystemReport getReport() {
        return report;
    }

    // Method to add entities to the ecosystem
    public void addEntity(EcosystemEntity entity) {
        entities.add(entity);
        report.recordPopulation(entity.getClass().getSimpleName()); // Record population
        report.recordInteraction(entity.getName() + " added to the ecosystem.");
    }

    public void removeEntity(EcosystemEntity entity) {
        entities.remove(entity);
        report.recordInteraction(entity.getName() + " removed from the ecosystem.");
    }

    public Plant findNearestPlant(int x, int y) {
        return entities.stream()
                .filter(e -> e instanceof Plant)
                .map(e -> (Plant) e)
                .min((p1, p2) -> distance(x, y, p1.x, p1.y) - distance(x, y, p2.x, p2.y))
                .orElse(null);
    }

    public Herbivore findNearestHerbivore(int x, int y) {
        return entities.stream()
                .filter(e -> e instanceof Herbivore)
                .map(e -> (Herbivore) e)
                .min((h1, h2) -> distance(x, y, h1.x, h1.y) - distance(x, y, h2.x, h2.y))
                .orElse(null);
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public void simulateStep() {
        List<EcosystemEntity> currentEntities = new ArrayList<>(entities);

        for (EcosystemEntity entity : currentEntities) {
            if (entity.isAlive()) {
                entity.act(this);  // Ensure each entity acts
            }
        }

        entities.removeIf(e -> !e.isAlive());

        // Trigger random events
        if (random.nextDouble() < randomEventProbability) {
            triggerRandomEvent();
        }

     //   displayMatrix();
    }

    public void triggerRandomEvent() {
        int eventType = random.nextInt(3);
        switch (eventType) {
            case 0 -> storm();
            case 1 -> drought();
            case 2 -> introduceNewSpecies();
        }
    }

    private void drought() {
        System.out.println("\n--- Random Event: Drought! ---");
        for (EcosystemEntity entity : entities) {
            if (entity instanceof Plant) {
                entity.energy -= 30; // Plants lose energy
                if (!entity.isAlive()) {
                    System.out.println(entity.name + " withered away due to the drought.");
                }
            }
        }
    }

    private void storm() {
        System.out.println("\n--- Random Event: Storm! ---");
        for (EcosystemEntity entity : new ArrayList<>(entities)) {
            if (random.nextDouble() > entity.survivalRate) {
                System.out.println(entity.name + " was lost in the storm.");
                entities.remove(entity);
                report.recordInteraction(entity.name + " was lost in the storm.");
            }
        }
    }

    private void introduceNewSpecies() {
        System.out.println("\n--- Random Event: New Species Appears! ---");
        int x = random.nextInt(gridSize);
        int y = random.nextInt(gridSize);

        // Randomly choose the type of new species to introduce
        int speciesType = random.nextInt(4); // 0: Plant, 1: Herbivore, 2: Omnivore, 3: Carnivore
        switch (speciesType) {
            case 0: // New Plant
                Plant newPlant = new Plant("New Plant", 50, x, y);
                addEntity(newPlant);
                System.out.println("A new plant species has appeared at (" + x + ", " + y + ").");
                break;
            case 1: // New Herbivore
                Herbivore newHerbivore = new Herbivore("New Herbivore", 80, x, y, 0.6, 2);
                addEntity(newHerbivore);
                System.out.println("A new herbivore species has appeared at (" + x + ", " + y + ").");
                break;
            case 2: // New Omnivore
                Omnivore newOmnivore = new Omnivore("New Omnivore", 100, x, y, 0.7, 2);
                addEntity(newOmnivore);
                System.out.println("A new omnivore species has appeared at (" + x + ", " + y + ").");
                break;
            case 3: // New Carnivore
                Carnivore newCarnivore = new Carnivore("New Carnivore", 120, x, y, 0.8, 3);
                addEntity(newCarnivore);
                System.out.println("A new carnivore species has appeared at (" + x + ", " + y + ").");
                break;
            default:
                break; // This should never happen
        }
    }

    public void generateFinalReport() {
        for (EcosystemEntity entity : entities) {
            String type = entity.getClass().getSimpleName();
            report.recordPopulation(type);
        }
        report.generateReport();
    }

    public void displayState() {
        System.out.println("\n--- Ecosystem State ---");
        for (EcosystemEntity entity : entities) {
            System.out.println(entity.name + " at (" + entity.x + ", " + entity.y + ") with energy: " + entity.energy);
        }
    }

    public void displayMatrix() {
        char[][] grid = new char[gridSize][gridSize]; // Updated grid size
        System.out.println("\n--- Ecosystem Grid ---");

        // Initialize the grid
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = '.';
            }
        }

        // Place entities in the grid
        for (EcosystemEntity entity : entities) {
            if (entity.x >= 0 && entity.x < gridSize && entity.y >= 0 && entity.y < gridSize) {
                char symbol = entity instanceof Plant ? 'P' :
                              entity instanceof Herbivore ? 'H' :
                              entity instanceof Carnivore ? 'C' : 'O'; // Omnivore
                grid[entity.x][entity.y] = symbol;
            }
        }

        // Display the grid
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}