package Model;

public class Dragon extends Enemy {

    public Dragon(String name, int health, int attackPower, double lootChance) {
        super(name, health, attackPower, lootChance);
    }

    @Override
    public void attack(Character target) {
        System.out.println(getName() + " breathes devastating fire upon " + target.getName() + "!");
        // Dragon deals base damage plus a flat bonus for fire
        int fireDamage = attackPower + 10; 
        target.takeDamage(fireDamage);
    }
}