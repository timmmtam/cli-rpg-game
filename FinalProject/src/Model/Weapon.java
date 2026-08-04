package Model;

import java.lang.annotation.Target;

public class Weapon extends Item {
	private int attackDamage;
	private boolean equipped;
	
	public Weapon(String name, String description, int value, int attackDamage) {
        super(name, description, value);
        this.attackDamage = attackDamage;
        this.equipped = false;
    }

    public Weapon(String name, int value, int attackDamage) {
        super(name, value);
        this.attackDamage = attackDamage;
        this.equipped = false;
    }
    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean isEquipped() {
        return equipped;
    }
    
    public String toString() {
        return super.toString() + String.format(" [+%d damage]%s",
                attackDamage, equipped ? " (equipped)" : "");
    }
    
    public void equip(Character target) {
    	target.setAttackPower(target.getAttackPower() + attackDamage);
    	equipped = true;
    }
}
