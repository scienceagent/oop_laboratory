package lab_3;

import java.util.Scanner;

public class EcosystemSimulation {
    public static void main(String[] args) {
        Ecosystem ecosystem = new Ecosystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=============================");
            System.out.println("     Ecosystem Simulation     ");
            System.out.println("==============================");
            System.out.println("1. Add a Plant - Add a new plant entity to the ecosystem.");
            System.out.println("2. Add a Herbivore - Add a new herbivore entity that eats plants.");
            System.out.println("3. Add a Carnivore - Add a new carnivore entity that eats herbivores.");
            System.out.println("4. Add an Omnivore - Add a new omnivore entity that eats both plants and herbivores.");
            System.out.println("5. Simulate Step - Advance the simulation by one time step, allowing entities to act.");
            System.out.println("6. Display Ecosystem State - Show the current state of all entities in the ecosystem.");
            System.out.println("7. Generate Final Report - Display the final report of the ecosystem.");
            System.out.println("8. Run Extended Simulation - Run a series of simulation steps automatically.");
            System.out.println("9. Exit - Exit the simulation program.");
            System.out.println("==============================");
            System.out.print("Choose an option (1-9): ");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addPlant(scanner, ecosystem);
                    break;
                case 2:
                    addHerbivore(scanner, ecosystem);
                    break;
                case 3:
                    addCarnivore(scanner, ecosystem);
                    break;
                case 4:
                    addOmnivore(scanner, ecosystem);
                    break;
                case 5:
                    ecosystem.simulateStep();
                    break;
                case 6:
                    ecosystem.displayState();
                    break;
                case 7:
                    ecosystem.generateFinalReport(); // Generate and display the final report
                    break;
                case 8:
                    runExtendedSimulation(scanner, ecosystem);
                    break;
                case 9:
                    System.out.println("Exiting the simulation. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        }
    }

    private static void addPlant(Scanner scanner, Ecosystem ecosystem) {
        System.out.print("Enter plant name: ");
        String plantName = scanner.next();
        System.out.print("Enter plant energy: ");
        int plantEnergy = scanner.nextInt();
        System.out.print("Enter plant coordinates (x y): ");
        int plantX = scanner.nextInt();
        int plantY = scanner.nextInt();
        ecosystem.addEntity(new Plant(plantName, plantEnergy, plantX, plantY));
        System.out.println("Plant added: " + plantName);
    }

    private static void addHerbivore(Scanner scanner, Ecosystem ecosystem) {
        System.out.print("Enter herbivore name: ");
        String herbName = scanner.next();
        System.out.print("Enter herbivore energy: ");
        int herbEnergy = scanner.nextInt();
        System.out.print("Enter herbivore coordinates (x y): ");
        int herbX = scanner.nextInt();
        int herbY = scanner.nextInt();
        System.out.print("Enter herbivore speed: ");
        int herbSpeed = scanner.nextInt();
        ecosystem.addEntity(new Herbivore(herbName, herbEnergy, herbX, herbY, 0.8, herbSpeed));
        System.out.println("Herbivore added: " + herbName);
    }

    private static void addCarnivore(Scanner scanner, Ecosystem ecosystem) {
        System.out.print("Enter carnivore name: ");
        String carnName = scanner.next();
        System.out.print("Enter carnivore energy: ");
        int carnEnergy = scanner.nextInt();
        System.out.print("Enter carnivore coordinates (x y): ");
        int carnX = scanner.nextInt();
        int carnY = scanner.nextInt();
        System.out.print("Enter carnivore speed: ");
        int carnSpeed = scanner.nextInt();
        ecosystem.addEntity(new Carnivore(carnName, carnEnergy, carnX, carnY, 0.6, carnSpeed));
        System.out.println("Carnivore added: " + carnName);
    }

    private static void addOmnivore(Scanner scanner, Ecosystem ecosystem) {
        System.out.print("Enter omnivore name: ");
        String omniName = scanner.next();
        System.out.print("Enter omnivore energy: ");
        int omniEnergy = scanner.nextInt();
        System.out.print("Enter omnivore coordinates (x y): ");
        int omniX = scanner.nextInt();
        int omniY = scanner.nextInt();
        System.out.print("Enter omnivore speed: ");
        int omniSpeed = scanner.nextInt();
        ecosystem.addEntity(new Omnivore(omniName, omniEnergy, omniX, omniY, 0.7, omniSpeed));
        System.out.println("Omnivore added: " + omniName);
    }

    private static void runExtendedSimulation(Scanner scanner, Ecosystem ecosystem) {
        System.out.print("Enter number of steps for the extended simulation: ");
        int steps = scanner.nextInt();
        ExtendedSimulation extendedSimulation = new ExtendedSimulation(ecosystem);
        extendedSimulation.run(steps);
        System.out.println("Extended simulation completed for " + steps + " steps.");
    }
}