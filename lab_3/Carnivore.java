package lab_3;

import java.util.Random;

class Carnivore extends Animal {

    public Carnivore(String name, int energy, int x, int y, double survivalRate, int speed) {
        super(name, energy, x, y, survivalRate, speed, "Carnivore");
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
            System.out.println(name + " at (" + x + ", " + y + ") attacks " + prey.name + "!");
            energy += prey.energy;
            ecosystem.removeEntity(prey);
        }
    }

    private boolean isNear(EcosystemEntity entity) {
        return Math.abs(entity.x - x) <= 1 && Math.abs(entity.y - y) <= 1;
    }

    private void reproduce(Ecosystem ecosystem) {
        if (energy > 150) {
            int newX = x + (new Random().nextInt(3) - 1);
            int newY = y + (new Random().nextInt(3) - 1);
            ecosystem.addEntity(new Carnivore(name + " Cub", 75, newX, newY, survivalRate, speed));
            energy -= 75;
        }
    }
}