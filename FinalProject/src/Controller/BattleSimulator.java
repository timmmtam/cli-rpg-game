package Controller;

import Model.Character;
import Model.Enemy;
import Model.Item;
import Model.Party;
import Model.Weapon;
import Model.Potion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BattleSimulator {

    public void startBattle(Party party, List<Enemy> enemies, Scanner scanner) {
        System.out.println("\n==================================");
        System.out.println("       A BATTLE HAS STARTED!      ");
        System.out.println("==================================");

        int turnNumber = 1;

        while (!party.isPartyDefeated() && !allEnemiesDefeated(enemies)) {
            System.out.println("\n=== Turn " + turnNumber + " ===");
            displayStatus(party, enemies);

            if (!allEnemiesDefeated(enemies)) {
                System.out.println("\n>> Choose actions for your party <<");
                for (Character c : party.getMembers()) {
                    if (!c.isAlive() || allEnemiesDefeated(enemies)) {
                        continue;
                    }
                    if (executePlayerTurn(c, party, enemies, scanner)) {
                        return;
                    }
                }
            }

            if (!party.isPartyDefeated() && !allEnemiesDefeated(enemies)) {
                executeEnemyTurn(party, enemies, scanner);
            }

            turnNumber++;
        }

        if (party.isPartyDefeated()) {
            System.out.println("\n==================================");
            System.out.println("    DEFEAT! The party has fallen. ");
            System.out.println("==================================");
        } else {
            System.out.println("\n==================================");
            System.out.println("  VICTORY! All enemies defeated.  ");
            System.out.println("==================================");
            int goldReward = 50;
            party.setGold(party.getGold() + goldReward);
            System.out.println("The party earned " + goldReward + " gold!");
        }
    }

    private boolean allEnemiesDefeated(List<Enemy> enemies) {
        for (Enemy e : enemies) {
            if (e.getHealth() > 0) {
                return false;
            }
        }
        return true;
    }

    private void displayStatus(Party party, List<Enemy> enemies) {
        System.out.println("\n--- Heroes ---");
        for (Character c : party.getMembers()) {
            if (c.isAlive()) {
                if (c instanceof Model.Mage mage) {
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

    private boolean executePlayerTurn(Character c, Party party, List<Enemy> enemies, Scanner scanner) {
        while (true) {
            System.out.println("\n" + c.getName() + "'s turn:");

            boolean isHealer = c instanceof Model.Healer;

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

            int action = getValidInput(scanner, 0, isHealer ? 3 : 2);

            if (action == 0) {
                System.out.println("You have fled the battle!");
                return true;
            }

            boolean performed = switch (action) {
                case 1 -> performAttack(c, enemies, scanner);
                case 2 -> {
                    if (isHealer) {
                        yield performHeal((Model.Healer) c, party, scanner);
                    } else {
                        yield performUseItem(c, party, scanner);
                    }
                }
                case 3 -> performUseItem(c, party, scanner);
                default -> false;
            };

            if (performed) {
                return false;
            }
        }
    }

    private boolean performAttack(Character attacker, List<Enemy> enemies, Scanner scanner) {
        List<Enemy> aliveEnemies = new ArrayList<>();
        for (Enemy e : enemies) {
            if (e.getHealth() > 0) {
                aliveEnemies.add(e);
            }
        }

        if (aliveEnemies.isEmpty()) {
            System.out.println("No enemies to attack!");
            pause(scanner);
            return true;
        }

        System.out.println("Select target:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            System.out.println((i + 1) + ". " + aliveEnemies.get(i).getName() + " [HP: " + aliveEnemies.get(i).getHealth() + "]");
        }
        System.out.println("0. Cancel");

        int targetChoice = getValidInput(scanner, 0, aliveEnemies.size());
        if (targetChoice == 0) {
            return false;
        }
        Enemy target = aliveEnemies.get(targetChoice - 1);

        int oldHealth = target.getHealth();
        System.out.println(attacker.getName() + " attacks " + target.getName() + "!");
        attacker.attack(target);
        int newHealth = target.getHealth();
        int damage = oldHealth - newHealth;
        if (damage > 0) {
            System.out.println("Dealt " + damage + " damage!");
        }

        if (target.getHealth() <= 0) {
            System.out.println(target.getName() + " has been defeated!");
            Item loot = target.dropLoot();
            if (loot != null) {
                System.out.println("Loot dropped: " + loot.getName());
                attacker.getInventory().addItem(loot);
            }
        }

        pause(scanner);
        return true;
    }

    private boolean performHeal(Model.Healer healer, Party party, Scanner scanner) {
        List<Character> aliveAllies = new ArrayList<>();
        for (Character c : party.getMembers()) {
            if (c.isAlive()) {
                aliveAllies.add(c);
            }
        }

        System.out.println("Select target to heal:");
        for (int i = 0; i < aliveAllies.size(); i++) {
            System.out.println((i + 1) + ". " + aliveAllies.get(i).getName() + " [HP: " + aliveAllies.get(i).getHealth() + "/" + aliveAllies.get(i).getMaxHealth() + "]");
        }
        System.out.println("0. Cancel");

        int targetChoice = getValidInput(scanner, 0, aliveAllies.size());
        if (targetChoice == 0) {
            return false;
        }
        Character target = aliveAllies.get(targetChoice - 1);

        int oldHealth = target.getHealth();
        healer.heal(target);
        int newHealth = target.getHealth();
        int healed = newHealth - oldHealth;
        System.out.println(healer.getName() + " heals " + target.getName() + " for " + healed + " HP!");

        pause(scanner);
        return true;
    }

    private boolean performUseItem(Character c, Party party, Scanner scanner) {
        List<Item> items = c.getInventory().getItems();

        if (items.isEmpty()) {
            System.out.println("Inventory is empty!");
            pause(scanner);
            return false;
        }

        System.out.println("Select item to use:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        System.out.println("0. Cancel");

        int itemChoice = getValidInput(scanner, 0, items.size());
        if (itemChoice == 0) {
            return false;
        }
        Item selectedItem = items.get(itemChoice - 1);

        if (selectedItem instanceof Model.Potion) {
            System.out.println("Select target:");
            for (int i = 0; i < party.getMembers().size(); i++) {
                Character target = party.getMembers().get(i);
                System.out.println((i + 1) + ". " + target.getName() + " [HP: " + target.getHealth() + "/" + target.getMaxHealth() + "]");
            }
            System.out.println("0. Cancel");

            int targetChoice = getValidInput(scanner, 0, party.getMembers().size());
            if (targetChoice == 0) {
                return false;
            }
            Character target = party.getMembers().get(targetChoice - 1);

            int oldHealth = target.getHealth();
            c.getInventory().useItem(selectedItem, target);
            int newHealth = target.getHealth();
            int healed = newHealth - oldHealth;
            System.out.println("Used " + selectedItem.getName() + " on " + target.getName() + "!");
            System.out.println("Healed " + healed + " HP!");
        } else if (selectedItem instanceof Model.Weapon) {
            c.getInventory().useItem(selectedItem, c);
            System.out.println("Equipped " + selectedItem.getName() + "!");
        }

        pause(scanner);
        return true;
    }

    private void pause(Scanner scanner) {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    private int getValidInput(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("Choice: ");
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through
            }
            System.out.println("Invalid choice! Please enter a number between " + min + " and " + max + ".");
        }
    }

    private void executeEnemyTurn(Party party, List<Enemy> enemies, Scanner scanner) {
        System.out.println("\n>> Enemy's Turn <<");
        for (Enemy e : enemies) {
            if (e.getHealth() <= 0) {
                continue;
            }

            Character target = getAliveCharacter(party);
            if (target == null) {
                break;
            }

            int oldHealth = target.getHealth();
            System.out.println(e.getName() + " attacks " + target.getName() + "!");
            e.attack(target);
            int newHealth = target.getHealth();
            int damage = oldHealth - newHealth;
            if (damage > 0) {
                System.out.println("Dealt " + damage + " damage!");
            }

            pause(scanner);
        }
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
