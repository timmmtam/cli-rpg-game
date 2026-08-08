package Model;

public class Goblin extends Enemy {
	private int speed;
	
	public Goblin(String name, int health, int attackPower, double lootChance, int speed) {
		super(name, health, attackPower, lootChance);
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void quickAttack(Character target) {
		int damage = getAttackPower() + speed;
		target.setHealth(target.getHealth() - damage);
	}
}