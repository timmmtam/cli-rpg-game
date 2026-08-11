package Model;

import java.lang.annotation.Target;

public class Weapon extends Item {
	private int attackDamage;
	private boolean equipped;
	
    private static final String[] WEAPON_NAMES = {
        "Rusty Sword", "Iron Dagger", "Steel Axe", "Battle Hammer",
        "War Blade", "Spiked Club", "Sharpened Blade", "Cursed Mace",
        "Dragon Slayer", "Goblin Cleaver"
    };

    private static final String[] WEAPON_DESCRIPTIONS = {
        "A dented but serviceable blade.",
        "A quick and lightweight weapon.",
        "A heavy axe for devastating strikes.",
        "Crushing power in every swing.",
        "A finely crafted sword of war.",
        "A crude but effective spiked weapon.",
        "Tempered steel with a keen edge.",
        "An ominous weapon that hums with dark energy.",
        "A legendary blade said to pierce dragon scales.",
        "A massive axe forged from goblin steel."
    };

    private static final java.util.Random RANDOM = new java.util.Random();

    public static Weapon createRandom() {
        int idx = RANDOM.nextInt(WEAPON_NAMES.length);
        int damage = 3 + RANDOM.nextInt(6);
        int value = 10 + damage * 5;
        return new Weapon(WEAPON_NAMES[idx], WEAPON_DESCRIPTIONS[idx], value, damage);
    }

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
    
    
    public void equip(Character target) {
    	target.setAttackPower(target.getAttackPower() + attackDamage);
    	equipped = true;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" [+%d damage]%s",
                attackDamage, equipped ? " (equipped)" : "");
    }
}
