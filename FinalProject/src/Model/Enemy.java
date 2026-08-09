package Model;

import java.util.Random;

//base class for enemies

public abstract class Enemy {

    private static final Random RANDOM = new Random();

    private String name;
    private int health;
    protected int attackPower;
    private double lootChance; // 0.0 - 1.0

    public Enemy(String name, int health, int attackPower, double lootChance) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.lootChance = lootChance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public double getLootChance() {
        return lootChance;
    }

    public void setLootChance(double lootChance) {
        this.lootChance = lootChance;
    }
    
    public Item dropLoot() {
        if (RANDOM.nextDouble() <= lootChance) {
            return new Potion(name + "'s Dropped Potion", "A potion looted from a fallen foe.", 15, 20);
        }
        return null;
    }
    
    public void takeDamage(int amount) {
    	   setHealth(getHealth() - amount);
    	}
    

    @Override
    public String toString() {
        return String.format("%s [%s] HP: %d", name, getClass().getSimpleName(), health);
    }
}
