package Model;

public class TestEnemies extends Enemy {
    public TestEnemies(String name, int health, int attackPower, double lootChance) {
        super(name, health, attackPower, lootChance);
    }
    
    @Override
    public void attack(Character target) {
        System.out.println(getName() + " strikes " + target.getName() + "!");
        target.takeDamage(attackPower);
    }
}