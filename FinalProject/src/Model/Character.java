package Model;

public abstract class Character {
	private String name;
    private int health;
    private int maxHealth;
    private int level;
    private int attackPower;
    private int defense;
    
    public Character(String name, int maxHealth, int level, int attackPower, int defense) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.level = level;
        this.attackPower = attackPower;
        this.defense = defense;
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
    
    public String toString() {
        return String.format("%s [Lv.%d %s] HP: %d/%d",
                name, level, getClass().getSimpleName(), health, maxHealth);
    }
}
