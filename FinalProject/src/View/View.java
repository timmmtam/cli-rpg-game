package View;

import Model.*;
import java.util.List;

public class View {

    // === Welcome & Setup ===
    public void printWelcome() {
        System.out.println("=================================================");
        System.out.println("        WELCOME TO THE CLI RPG GAME ENGINE       ");
        System.out.println("=================================================");
        System.out.println();
    }

    public void printPartyCreationPrompt() {
        System.out.println("--- Create Your Party ---");
    }

    public void printEnterPartyName() {
        System.out.print("Enter party name: ");
    }

    public void printClassSelectionHeader() {
        System.out.println("\n=================================================");
        System.out.println("           HERO NAME & CLASS SELECTION           ");
        System.out.println("=================================================");
        System.out.println("--- Class Info ---:");
        System.out.println("1. Warrior - High HP, high attack");
        System.out.println("2. Mage    - High magic damage, mana-based");
        System.out.println("3. Healer  - Can heal party members");
    }

    public void printEnterHeroName(int index) {
        System.out.print("\nEnter name for hero " + index + ": ");
    }

    public void printChooseClass(String name) {
        System.out.println("Choose class for " + name + ":");
    }

    public void printClassOptions() {
        System.out.println("1. Warrior");
        System.out.println("2. Mage");
        System.out.println("3. Healer");
    }

    public void printChoicePrompt() {
        System.out.print("Choice: ");
    }

    public void printHeroJoined(String name, String className) {
        System.out.println(name + " the " + className + " has joined!");
    }

    public void printPartyCreated(int gold) {
        System.out.println("\nParty created with " + gold + " gold.");
    }

    // === Main Menu ===
    public void printEncounterHeader(int encounterNumber) {
        System.out.println("\n=================================================");
        System.out.println("  ENCOUNTER #" + encounterNumber);
        System.out.println("=================================================");
    }

    public void printMainMenu() {
        System.out.println("1. Fight enemies");
        System.out.println("2. Rest (heal party +30HP - costs 50 gold)");
        System.out.println("3. Check party status");
        System.out.println("4. Use items from inventory");
        System.out.println("5. Quit game");
        System.out.print("\nChoice: ");
    }

    public void printThanksForPlaying() {
        System.out.println("Thanks for playing!");
    }

    public void printGameOver(int enemiesDefeated) {
        System.out.println("\n=================================================");
        System.out.println("  GAME OVER - Your party has been defeated!     ");
        System.out.println("  Enemies defeated: " + enemiesDefeated);
        System.out.println("=================================================");
    }

    public void printInvalidChoice() {
        System.out.println("Invalid choice!");
    }

    // === Battle ===
    public void printEncountered(List<Enemy> enemies) {
        System.out.println("\nEncountered:");
        for (Enemy e : enemies) {
            System.out.println("  - " + e);
        }
    }

    public void printBattleStart() {
        System.out.println("\n==================================");
        System.out.println("       A BATTLE HAS STARTED!      ");
        System.out.println("==================================");
    }

    public void printTurnHeader(int turnNumber) {
        System.out.println("\n=== Turn " + turnNumber + " ===");
    }

    public void displayStatus(Party party, List<Enemy> enemies) {
        System.out.println("\n--- Heroes ---");
        for (Model.Character c : party.getMembers()) {
            if (c.isAlive()) {
                if (c instanceof Mage mage) {
                    System.out.println(c.getName() + " [" + c.getClass().getSimpleName() + "] HP: " + c.getHealth() + "/" + c.getMaxHealth() + " | Mana: " + mage.getMana() + "/" + mage.getMaxMana());
                } else {
                    System.out.println(c.getName() + " [" + c.getClass().getSimpleName() + "] HP: " + c.getHealth() + "/" + c.getMaxHealth());
                }
            } else {
                System.out.println(c.getName() + " [" + c.getClass().getSimpleName() + "] - DEAD");
            }
        }

        System.out.println("\n--- Enemies ---");
        for (Enemy e : enemies) {
            if (e.getHealth() > 0) {
                System.out.println(e.getName() + " [" + e.getClass().getSimpleName() + "] HP: " + e.getHealth());
            } else {
                System.out.println(e.getName() + " [" + e.getClass().getSimpleName() + "] - DEFEATED");
            }
        }
    }

    public void printPlayerTurnHeader(String name) {
        System.out.println("\n" + name + "'s turn:");
    }

    public void printActionMenu(boolean isHealer) {
        if (isHealer) {
            System.out.println("1. Attack");
            System.out.println("2. Heal Ally");
            System.out.println("3. Use Item");
            System.out.println("0. Quit Battle");
        } else {
            System.out.println("1. Attack");
            System.out.println("2. Use Item");
            System.out.println("0. Quit Battle");
        }
    }

    public void printTargetOptions(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("0. Cancel");
    }

    public void printAttackResult(String attacker, String target, int damage) {
        System.out.println(attacker + " attacks " + target + "!");
        if (damage > 0) {
            System.out.println("Dealt " + damage + " damage!");
        }
    }

    public void printEnemyDefeated(String name) {
        System.out.println(name + " has been defeated!");
    }

    public void printLootDropped(String itemName) {
        System.out.println("Loot dropped: " + itemName);
    }

    public void printHealResult(String healer, String target, int amount) {
        System.out.println(healer + " heals " + target + " for " + amount + " HP!");
    }

    public void printItemUsed(String itemName, String targetName) {
        System.out.println("Used " + itemName + " on " + targetName + "!");
    }

    public void printItemEquipped(String itemName) {
        System.out.println("Equipped " + itemName + "!");
    }

    public void printHealedAmount(int amount) {
        System.out.println("Healed " + amount + " HP!");
    }

    public void printDefend(String name) {
        System.out.println(name + " defends and braces for impact!");
    }

    public void printEnemyTurnHeader() {
        System.out.println("\n>> Enemy's Turn <<");
    }

    public void printEnemyAttack(String enemy, String target) {
        System.out.println(enemy + " attacks " + target + "!");
    }

    public void printNoEnemiesToAttack() {
        System.out.println("No enemies to attack!");
    }

    public void printInventoryEmpty() {
        System.out.println("Inventory is empty!");
    }

    public void printQuitBattle() {
        System.out.println("You have fled the battle!");
    }

    public void printVictory(int gold) {
        System.out.println("\n==================================");
        System.out.println("  VICTORY! All enemies defeated.  ");
        System.out.println("==================================");
        System.out.println("The party earned " + gold + " gold!");
    }

    public void printDefeat() {
        System.out.println("\n==================================");
        System.out.println("    DEFEAT! The party has fallen. ");
        System.out.println("==================================");
    }

    public void printPressEnterToContinue() {
        System.out.print("Press Enter to continue...");
    }

    // === Party & Inventory ===
    public void printPartyStatus(Party party) {
        System.out.println("\n--- Party Status ---");
        System.out.println("Gold: " + party.getGold());
        System.out.println("Members:");
        for (Model.Character c : party.getMembers()) {
            if (c instanceof Mage mage) {
                System.out.println("  " + c.getName() + " [Lv." + c.getLevel() + " " + c.getClass().getSimpleName() + "] HP: " + c.getHealth() + "/" + c.getMaxHealth() + " | Mana: " + mage.getMana() + "/" + mage.getMaxMana());
            } else {
                System.out.println("  " + c.getName() + " [Lv." + c.getLevel() + " " + c.getClass().getSimpleName() + "] HP: " + c.getHealth() + "/" + c.getMaxHealth());
            }
        }
    }

    public void printInventoryManagement(Party party) {
        System.out.println("\n--- Inventory Management ---");
        int memberIndex = 1;
        for (Model.Character c : party.getMembers()) {
            System.out.println(memberIndex + ". " + c.getName() + " (" + c.getClass().getSimpleName() + ")");
            List<Item> items = c.getInventory().getItems();
            if (items.isEmpty()) {
                System.out.println("   Inventory is empty.");
            } else {
                for (int i = 0; i < items.size(); i++) {
                    System.out.println("   " + (i + 1) + ". " + items.get(i));
                }
            }
            memberIndex++;
        }
    }

    public void printRestMessage(int goldRemaining) {
        System.out.println("Party rested and recovered 30 HP each for 50 gold.");
        System.out.println("Remaining gold: " + goldRemaining);
    }

    public void printNotEnoughGold(int amount) {
        System.out.println("Not enough gold to rest! (Need " + amount + " gold)");
    }

    public void printBattleWon() {
        System.out.println("\nBattle won! Press Enter to continue...");
    }
}
