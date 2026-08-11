package Model;

public class Potion extends Item {

    private int healAmount;

    public Potion(String name, String description, int value, int healAmount) {
        super(name, description, value);
        this.healAmount = healAmount;
    }

    public Potion(String name, int value, int healAmount) {
        super(name, value);
        this.healAmount = healAmount;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
    @Override
    public String toString() {
        return super.toString() + String.format(" [Heals %d HP]", healAmount);
    }
    
    public void use(Character target) {
        target.setHealth(target.getHealth() + healAmount);
    }
}
