import Model.*;

public class Main {
    public static void main(String[] args) {
        Warrior hero = new Warrior("Darve", 250, 1, 20, 5, 8, 4);
        Enemy dummy = new TestEnemies("Training Dummy", 100, 5, 0.0);

        System.out.println("Enemy HP before: " + dummy.getHealth());
        hero.attack(dummy);
        System.out.println("Enemy HP after: " + dummy.getHealth());
    }
}