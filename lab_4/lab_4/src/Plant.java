

import java.util.Random;

class Plant extends EcosystemEntity {

    public Plant(String name, int energy, int x, int y) {
        super(name, energy, x, y, 1.0);
        System.out.println("Plant \"" + name + "\" added at position (" + x + ", " + y + ") with energy " + energy + ".");
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
            int newX = Math.max(0, Math.min(9, x + (new Random().nextInt(3) - 1)));
            int newY = Math.max(0, Math.min(9, y + (new Random().nextInt(3) - 1)));
            ecosystem.addEntity(new Plant(name + " Seedling", 20, newX, newY));
            energy -= 20;
        }
    }

}