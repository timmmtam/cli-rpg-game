package Model;

public class Dragon extends Enemy {
	private int firePower;
	
	public Dragon(String name, int health, int attackPower, double lootChance, int firePower) {
		super(name, health, attackPower, lootChance);
		this.firePower = firePower;
	}

	public int getFirePower() {
		return firePower;
	}

	public void setFirePower(int firePower) {
		this.firePower = firePower;
	}
	
	public void fireAttack(Character target) {
		int damage = getAttackPower() + firePower;
		target.setHealth(target.getHealth() - damage);
	}
}