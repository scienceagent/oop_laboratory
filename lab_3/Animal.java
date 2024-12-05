package lab_3;

import java.util.Random;

abstract class Animal extends EcosystemEntity {
    protected int speed;
    protected String dietType;

    public Animal(String name, int energy, int x, int y, double survivalRate, int speed, String dietType) {
        super(name, energy, x, y, survivalRate);
        this.speed = speed;
        this.dietType = dietType;
    }

    public abstract void eat(Ecosystem ecosystem);

    public void move() {
        Random rand = new Random();
        x += rand.nextInt(2 * speed + 1) - speed;
        y += rand.nextInt(2 * speed + 1) - speed;
    }
}