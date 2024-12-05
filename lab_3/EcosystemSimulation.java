package lab_3;

import java.util.Scanner;

public class EcosystemSimulation {
    public static void main(String[] args) {
        Ecosystem ecosystem = new Ecosystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEcosystem Simulation Menu:");
            System.out.println("1. Add a Plant");
            System.out.println("2. Add a Herbivore");
            System.out.println("3. Add a Carnivore");
            System.out.println("4. Add an Omnivore");
            System.out.println("5. Simulate Step");
            System.out.println("6. Display Ecosystem State");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter plant name, energy, x, y: ");
                    String plantName = scanner.next();
                    int plantEnergy = scanner.nextInt();
                    int plantX = scanner.nextInt();
                    int plantY = scanner.nextInt();
                    ecosystem.addEntity(new Plant(plantName, plantEnergy, plantX, plantY));
                    break;
                case 2:
                    System.out.print("Enter herbivore name, energy, x, y, speed: ");
                    String herbName = scanner.next();
                    int herbEnergy = scanner.nextInt();
                    int herbX = scanner.nextInt();
                    int herbY = scanner.nextInt();
                    int herbSpeed = scanner.nextInt();
                    ecosystem.addEntity(new Herbivore(herbName, herbEnergy, herbX, herbY, 0.8, herbSpeed));
                    break;
                case 3:
                    System.out.print("Enter carnivore name, energy, x, y, speed: ");
                    String carnName = scanner.next();
                    int carnEnergy = scanner.nextInt();
                    int carnX = scanner.nextInt();
                    int carnY = scanner.nextInt();
                    int carnSpeed = scanner.nextInt();
                    ecosystem.addEntity(new Carnivore(carnName, carnEnergy, carnX, carnY, 0.6, carnSpeed));
                    break;
                case 4:
                    System.out.print("Enter omnivore name, energy, x, y, speed: ");
                    String omniName = scanner.next();
                    int omniEnergy = scanner.nextInt();
                    int omniX = scanner.nextInt();
                    int omniY = scanner.nextInt();
                    int omniSpeed = scanner.nextInt();
                    ecosystem.addEntity(new Omnivore(omniName, omniEnergy, omniX, omniY, 0.7, omniSpeed));
                    break;
                case 5:
                    ecosystem.simulateStep();
                    break;
                case 6:
                    ecosystem.displayState();
                    break;
                case 7:
                    System.out.println("Exiting the simulation. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}