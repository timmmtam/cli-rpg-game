package Model;

public class Goblin extends Enemy {

    public Goblin(String name, int health, int attackPower, double lootChance) {
        super(name, health, attackPower, lootChance);
    }

    @Override
    public void attack(Character target) {
        System.out.println(getName() + " wildly slashes at " + target.getName() + "!");
        target.takeDamage(attackPower); // Uses the protected attackPower directly
    }
}