package lab_3;

import java.util.Random;

class Plant extends EcosystemEntity {

    public Plant(String name, int energy, int x, int y) {
        super(name, energy, x, y, 1.0);
    }

    @Override
    public void act(Ecosystem ecosystem) {
        grow();
        System.out.println(name + " at (" + x + ", " + y + ") is acting... Grows and energy increases by 10.");
        reproduce(ecosystem);
    }

    private void grow() {
        energy += 10;
    }

    private void reproduce(Ecosystem ecosystem) {
        if (energy > 50) {
            int newX = x + (new Random().nextInt(3) - 1);
            int newY = y + (new Random().nextInt(3) - 1);
            ecosystem.addEntity(new Plant(name + " Seedling", 20, newX, newY));
            energy -= 20;
        }
    }
}