package Model;

public abstract class Character {
	private String name;
    private int health;
    private int maxHealth;
    private int level;
    protected int attackPower;
    protected int defense;
    private Inventory inventory;
    
    public Character(String name, int maxHealth, int level, int attackPower, int defense) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.level = level;
        this.attackPower = attackPower;
        this.defense = defense;
        this.inventory = new Inventory(10);
    }
    
  
    public void takeDamage(int amount) {
        int reduced = Math.max(amount - defense, 0);
        health = Math.max(health - reduced, 0);
        System.out.println(name + " takes " + reduced+ " damage! (" + health + "/" + maxHealth + " HP)");
    }

    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
    }

    public boolean isAlive() {
        return health > 0;
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


    public Inventory getInventory() { 
    	return inventory; 
    } 
    public abstract void attack(Enemy target); 

    @Override
    public String toString() {
        return String.format("%s [Lv.%d %s] HP: %d/%d",
                name, level, getClass().getSimpleName(), health, maxHealth);
    }
}
