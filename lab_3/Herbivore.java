package lab_3;

import java.util.Random;

class Herbivore extends Animal {

    public Herbivore(String name, int energy, int x, int y, double survivalRate, int speed) {
        super(name, energy, x, y, survivalRate, speed, "Herbivore");
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
        Plant plant = ecosystem.findNearestPlant(x, y);
        if (plant != null && isNear(plant)) {
            System.out.println(name + " at (" + x + ", " + y + ") eats " + plant.name + "!");
            energy += plant.energy;
            ecosystem.removeEntity(plant);
        }
    }

    private boolean isNear(EcosystemEntity entity) {
        return Math.abs(entity.x - x) <= 1 && Math.abs(entity.y - y) <= 1;
    }

    private void reproduce(Ecosystem ecosystem) {
        if (energy > 100) {
            int newX = x + (new Random().nextInt(3) - 1);
            int newY = y + (new Random().nextInt(3) - 1);
            ecosystem.addEntity(new Herbivore(name + " Offspring", 50, newX, newY, survivalRate, speed));
            energy -= 50;
        }
    }
}