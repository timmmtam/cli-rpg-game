package Model;

public class Healer extends Character{
	private int healingPower;
	public Healer(String name, int maxHealth, int level, int attackPower, int defense, int healingPower) {
        super(name, maxHealth, level, attackPower, defense);
        this.healingPower = healingPower;
    }
	public int getHealingPower() {
		return healingPower;
	}
	public void setHealingPower(int healingPower) {
		this.healingPower = healingPower;
	}
	public void attack(Enemy target) {
		target.takeDamage(getAttackPower());
	}
	public void heal(Character target) {
        target.setHealth(target.getHealth() + healingPower);
    }

}
