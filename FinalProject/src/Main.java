import Model.*;

public class Main {
    public static void main(String[] args) {
        Warrior hero = new Warrior("Darve", 250, 1, 20, 5, 8, 4);
        Enemy dummy = new TestEnemies("Training Dummy", 100, 5, 0.0);

        System.out.println("Enemy HP before: " + dummy.getHealth());
        hero.attack(dummy);
        System.out.println("Enemy HP after: " + dummy.getHealth());
        
//        test mage 
        System.out.println("mage test");
        Mage hero2 = new Mage("eudora", 120, 1, 8, 3, 50, 15);
        Enemy dummy2 = new TestEnemies("turret", 100, 5, 0.0);
        

		System.out.println("Mana before: " + hero2.getMana());
		System.out.println("Dummy HP before: " + dummy2.getHealth());
		
		hero2.attack(dummy2);
		
		System.out.println("Mana after: " + hero2.getMana());
		System.out.println("Dummy HP after: " + dummy2.getHealth());

//		testung healer
		System.out.println("healer test");
		Healer estes = new Healer("estes", 100, 1, 5, 2, 20);
		estes.setHealth(50); 

		System.out.println("estes HP before heal: " + estes.getHealth());
		estes.heal(estes); // test to healing self
		System.out.println("estes HP after heal: " + estes.getHealth());
		
//		test party
		System.out.println("partu test");
		Party myParty = new Party(50);
		myParty.addMember(hero);  
		myParty.addMember(hero2);
		myParty.addMember(estes);

		System.out.println("Party size: " + myParty.getMembers().size());
		System.out.println("Gold: " + myParty.getGold());
		System.out.println("Is party defeated? " + myParty.isPartyDefeated());

    }
}