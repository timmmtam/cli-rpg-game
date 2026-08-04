package Model;

public class Warrior extends Character {
	private int strength;
	private int armor;
	
	public Warrior(String name, int maxHealth, int level,  int attackPower, int defense, int strength, int armor) {
		super(name, maxHealth, level, attackPower, defense);
		this.strength = strength;
		this.armor = armor;
		
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}
	
	public void attack(Enemy target) {
		int damage = getAttackPower()+getStrength();
		target.takeDamage(damage);
	}
	
}
