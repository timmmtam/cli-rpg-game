package Controller;

import Model.Character;
import Model.Enemy;
import Model.Item;
import Model.Party;

import java.util.List;

public class BattleSimulator {

    //Main loop that runs the combat until a win or loss condition is met.
    public void startBattle(Party party, List<Enemy> enemies) {
        System.out.println("\n==================================");
        System.out.println("       A BATTLE HAS STARTED!      ");
        System.out.println("==================================");
        
        boolean battleOngoing = true;
        int turnNumber = 1;

        while (battleOngoing) {
            System.out.println("\n--- Turn " + turnNumber + " ---");
            executeTurnOrder(party, enemies);
            battleOngoing = !checkWinLossCondition(party, enemies);
            turnNumber++;
        }
    }


    //Handles the sequence of attacks for one full round of combat.
    public void executeTurnOrder(Party party, List<Enemy> enemies) {
        
        // 1. PLAYER TURN
        System.out.println(">> Party's Turn <<");
        for (Character c : party.getMembers()) {
            if (!c.isAlive()) {
                continue; // Skip dead characters
            }

            // Find the first alive enemy to attack
            Enemy target = getAliveEnemy(enemies);
            if (target == null) {
                break; // Combat ends if no enemies remain
            }

            System.out.println(c.getName() + " attacks " + target.getName() + "!");
            c.attack(target); // Polymorphic attack based on subclass (Mage, Warrior, etc.)
            
            // Check if the enemy died from this attack
            if (target.getHealth() <= 0) {
                System.out.println(target.getName() + " has been defeated!");
                
                // Roll for loot drop
                Item loot = target.dropLoot(); 
                if (loot != null) {
                    System.out.println("Loot dropped: " + loot.getName());
                    // Assign loot to the inventory of the character who got the kill
                    c.getInventory().addItem(loot); 
                }
            }
        }

        // 2. ENEMY TURN
        System.out.println("\n>> Enemy's Turn <<");
        for (Enemy e : enemies) {
            if (e.getHealth() <= 0) {
                continue; // Skip dead enemies
            }

            // Find the first alive party member to attack
            Character target = getAliveCharacter(party);
            if (target == null) {
                break; // Combat ends if party is wiped out
            }

            System.out.println(e.getName() + " attacks " + target.getName() + "!");
            
            // NOTE: Since the Enemy class in your source code currently lacks the abstract 
            // attack() method mentioned in the report, we calculate damage directly here
            target.takeDamage(e.getAttackPower());
        }
    }

    //Evaluates if the combat should end.
    public boolean checkWinLossCondition(Party party, List<Enemy> enemies) {
        
        // Check Loss
        if (party.isPartyDefeated()) {
            System.out.println("\n==================================");
            System.out.println("    DEFEAT! The party has fallen. ");
            System.out.println("==================================");
            return true;
        }

        // Check Win
        boolean allEnemiesDefeated = true;
        for (Enemy e : enemies) {
            if (e.getHealth() > 0) {
                allEnemiesDefeated = false;
                break;
            }
        }

        if (allEnemiesDefeated) {
            System.out.println("\n==================================");
            System.out.println("  VICTORY! All enemies defeated.  ");
            System.out.println("==================================");
            
            // Reward gold to the party
            int goldReward = 50; 
            party.setGold(party.getGold() + goldReward);
            System.out.println("The party earned " + goldReward + " gold!");
            return true;
        }

        return false;
    }

    //Helper Methods
    private Enemy getAliveEnemy(List<Enemy> enemies) {
        for (Enemy e : enemies) {
            if (e.getHealth() > 0) {
                return e;
            }
        }
        return null;
    }

    private Character getAliveCharacter(Party party) {
        for (Character c : party.getMembers()) {
            if (c.isAlive()) {
                return c;
            }
        }
        return null;
    }
}