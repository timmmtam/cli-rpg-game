package Model;

public class Mage extends Character {
	private int mana;
    private int maxMana;
    private int spellPower;

    public Mage(String name, int maxHealth, int level, int attackPower, int defense, int maxMana, int spellPower) {
        super(name, maxHealth, level, attackPower, defense);
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.spellPower = spellPower;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }

    public void attack(Enemy target) {
        int manaCost = 10;
        if (mana >= manaCost) {
            setMana(mana - manaCost);
            int damage = getAttackPower() + spellPower;
            target.takeDamage(damage);
        } else {
            target.takeDamage(getAttackPower()); 
        }
    }
}
