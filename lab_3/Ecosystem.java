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

    public void addEntity(EcosystemEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(EcosystemEntity entity) {
        entities.remove(entity);
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
        System.out.println("\n--- Simulation Step ---");
        List<EcosystemEntity> currentEntities = new ArrayList<>(entities);

        for (EcosystemEntity entity : currentEntities) {
            if (entity.isAlive()) {
                entity.act(this);
            }
        }

        entities.removeIf(e -> !e.isAlive());

        // Trigger random events
        if (random.nextDouble() < randomEventProbability) {
            triggerRandomEvent();
        }

        displayMatrix();
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
            }
        }
    }

    private void introduceNewSpecies() {
        System.out.println("\n--- Random Event: New Species Appears! ---");
        int x = random.nextInt(gridSize);
        int y = random.nextInt(gridSize);
        int energy = 100;
        double survivalRate = 0.7;
        int speed = 2;

        Omnivore newOmnivore = new Omnivore("New Omnivore", energy, x, y, survivalRate, speed);
        addEntity(newOmnivore);
        System.out.println("A new omnivore species has appeared at (" + x + ", " + y + ").");
    }

    public void generateFinalReport() {
        System.out.println("\n--- Final Ecosystem Report ---");
        Map<String, Long> populationCounts = new HashMap<>();

        for (EcosystemEntity entity : entities) {
            String type = entity.getClass().getSimpleName();
            populationCounts.put(type, populationCounts.getOrDefault(type, 0L) + 1);
        }

        for (Map.Entry<String, Long> entry : populationCounts.entrySet()) {
            System.out.println(entry.getKey() + " population: " + entry.getValue());
        }
    }

    public void displayState() {
        System.out.println("\n--- Ecosystem State ---");
        for (EcosystemEntity entity : entities) {
            System.out.println(entity.name + " at (" + entity.x + ", " + entity.y + ") with energy: " + entity.energy);
        }
    }

    public void displayMatrix() {
        char[][] grid = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = '.';
            }
        }

        for (EcosystemEntity entity : entities) {
            if (entity.x >= 0 && entity.x < gridSize && entity.y >= 0 && entity.y < gridSize) {
                char symbol = entity instanceof Plant ? 'P' :
                              entity instanceof Herbivore ? 'H' :
                              entity instanceof Carnivore ? 'C' :
                              'O'; // Omnivore
                grid[entity.x][entity.y] = symbol;
            }
        }

        System.out.println("\n--- Ecosystem Grid ---");
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}