package Controller;

import Model.*;
import View.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BattleSimulator {

    private final View view;

    public BattleSimulator(View view) {
        this.view = view;
    }

    public void startBattle(Party party, List<Enemy> enemies, Scanner scanner) {
        view.printBattleStart();

        int turnNumber = 1;

        while (!party.isPartyDefeated() && !allEnemiesDefeated(enemies)) {
            view.printTurnHeader(turnNumber);
            view.displayStatus(party, enemies);

            if (!allEnemiesDefeated(enemies)) {
                System.out.println("\n>> Choose actions for your party <<");
                for (Model.Character c : party.getMembers()) {
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
            view.printDefeat();
        } else {
            int goldReward = 50;
            party.setGold(party.getGold() + goldReward);
            view.printVictory(goldReward);
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

    private boolean executePlayerTurn(Model.Character c, Party party, List<Enemy> enemies, Scanner scanner) {
        while (true) {
            view.printPlayerTurnHeader(c.getName());

            boolean isHealer = c instanceof Model.Healer;

            view.printActionMenu(isHealer);

            int action = getValidInput(scanner, 0, isHealer ? 3 : 2);

            if (action == 0) {
                view.printQuitBattle();
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

    private boolean performAttack(Model.Character attacker, List<Enemy> enemies, Scanner scanner) {
        List<Enemy> aliveEnemies = new ArrayList<>();
        for (Enemy e : enemies) {
            if (e.getHealth() > 0) {
                aliveEnemies.add(e);
            }
        }

        if (aliveEnemies.isEmpty()) {
            view.printNoEnemiesToAttack();
            pause(scanner);
            return true;
        }

        List<String> options = new ArrayList<>();
        for (Enemy e : aliveEnemies) {
            options.add(e.getName() + " [HP: " + e.getHealth() + "]");
        }

        System.out.println("Select target:");
        view.printTargetOptions(options);

        int targetChoice = getValidInput(scanner, 0, aliveEnemies.size());
        if (targetChoice == 0) {
            return false;
        }
        Enemy target = aliveEnemies.get(targetChoice - 1);

        int oldHealth = target.getHealth();
        view.printAttackResult(attacker.getName(), target.getName(), 0);
        attacker.attack(target);
        int newHealth = target.getHealth();
        int damage = oldHealth - newHealth;
        if (damage > 0) {
            System.out.println("Dealt " + damage + " damage!");
        }

        if (target.getHealth() <= 0) {
            view.printEnemyDefeated(target.getName());
            Item loot = target.dropLoot();
            if (loot != null) {
                view.printLootDropped(loot.getName());
                attacker.getInventory().addItem(loot);
            }
        }

        pause(scanner);
        return true;
    }

    private boolean performHeal(Model.Healer healer, Party party, Scanner scanner) {
        List<Model.Character> aliveAllies = new ArrayList<>();
        for (Model.Character c : party.getMembers()) {
            if (c.isAlive()) {
                aliveAllies.add(c);
            }
        }

        List<String> options = new ArrayList<>();
        for (Model.Character c : aliveAllies) {
            options.add(c.getName() + " [HP: " + c.getHealth() + "/" + c.getMaxHealth() + "]");
        }

        System.out.println("Select target to heal:");
        view.printTargetOptions(options);

        int targetChoice = getValidInput(scanner, 0, aliveAllies.size());
        if (targetChoice == 0) {
            return false;
        }
        Model.Character target = aliveAllies.get(targetChoice - 1);

        int oldHealth = target.getHealth();
        healer.heal(target);
        int newHealth = target.getHealth();
        int healed = newHealth - oldHealth;
        view.printHealResult(healer.getName(), target.getName(), healed);

        pause(scanner);
        return true;
    }

    private boolean performUseItem(Model.Character c, Party party, Scanner scanner) {
        List<Item> items = c.getInventory().getItems();

        if (items.isEmpty()) {
            view.printInventoryEmpty();
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
            List<String> options = new ArrayList<>();
            for (Model.Character target : party.getMembers()) {
                options.add(target.getName() + " [HP: " + target.getHealth() + "/" + target.getMaxHealth() + "]");
            }

            System.out.println("Select target:");
            view.printTargetOptions(options);

            int targetChoice = getValidInput(scanner, 0, party.getMembers().size());
            if (targetChoice == 0) {
                return false;
            }
            Model.Character target = party.getMembers().get(targetChoice - 1);

            int oldHealth = target.getHealth();
            c.getInventory().useItem(selectedItem, target);
            int newHealth = target.getHealth();
            int healed = newHealth - oldHealth;
            view.printItemUsed(selectedItem.getName(), target.getName());
            view.printHealedAmount(healed);
        } else if (selectedItem instanceof Model.Weapon) {
            c.getInventory().useItem(selectedItem, c);
            view.printItemEquipped(selectedItem.getName());
        }

        pause(scanner);
        return true;
    }

    private void pause(Scanner scanner) {
        view.printPressEnterToContinue();
        scanner.nextLine();
    }

    private int getValidInput(Scanner scanner, int min, int max) {
        while (true) {
            view.printChoicePrompt();
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through
            }
            view.printInvalidChoice();
        }
    }

    private void executeEnemyTurn(Party party, List<Enemy> enemies, Scanner scanner) {
        view.printEnemyTurnHeader();
        for (Enemy e : enemies) {
            if (e.getHealth() <= 0) {
                continue;
            }

            Model.Character target = getAliveCharacter(party);
            if (target == null) {
                break;
            }

            int oldHealth = target.getHealth();
            view.printEnemyAttack(e.getName(), target.getName());
            e.attack(target);
            int newHealth = target.getHealth();
            int damage = oldHealth - newHealth;
            if (damage > 0) {
                System.out.println("Dealt " + damage + " damage!");
            }

            pause(scanner);
        }
    }

    private Model.Character getAliveCharacter(Party party) {
        for (Model.Character c : party.getMembers()) {
            if (c.isAlive()) {
                return c;
            }
        }
        return null;
    }
}
