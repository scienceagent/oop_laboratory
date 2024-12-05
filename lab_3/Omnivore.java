package lab_3;

import java.util.Random;

class Omnivore extends Animal {

    public Omnivore(String name, int energy, int x, int y, double survivalRate, int speed) {
        super(name, energy, x, y, survivalRate, speed, "Omnivore");
    }

    @Override
    public void act(Ecosystem ecosystem) {
        move();
        System.out.println(name + " at (" + x + ", " + y + ") is acting... Moves to (" + x + ", " + y + ").");
        eat(ecosystem);
        reproduce(ecosystem);
    }

    @Override
    public void eat(Ecosystem ecosystem) {
        Herbivore prey = ecosystem.findNearestHerbivore(x, y);
        if (prey != null && isNear(prey)) {
            System.out.println(name + " at (" + x + ", " + y + ") attacks and eats " + prey.name + "!");
            energy += prey.energy;
            ecosystem.removeEntity(prey);
        } else {
            Plant plant = ecosystem.findNearestPlant(x, y);
            if (plant != null && isNear(plant)) {
                System.out.println(name + " at (" + x + ", " + y + ") eats " + plant.name + "!");
                energy += plant.energy;
                ecosystem.removeEntity(plant);
            }
        }
    }

    private boolean isNear(EcosystemEntity entity) {
        return Math.abs(entity.x - x) <= 1 && Math.abs(entity.y - y) <= 1;
    }

    private void reproduce(Ecosystem ecosystem) {
        if (energy > 120) {
            int newX = x + (new Random().nextInt(3) - 1);
            int newY = y + (new Random().nextInt(3) - 1);
            ecosystem.addEntity(new Omnivore(name + " Cub", 60, newX, newY, survivalRate, speed));
            energy -= 60;
            System.out.println(name + " reproduced! New offspring added at (" + newX + ", " + newY + ").");
        }
    }
}