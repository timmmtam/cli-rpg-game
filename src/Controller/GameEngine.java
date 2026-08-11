package Controller;

import Model.*;
import View.View;
import java.util.*;

public class GameEngine {
    private Party party;
    private Scanner scanner;
    private Random random;
    private int enemiesDefeated;
    private final View view;

    public GameEngine() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.enemiesDefeated = 0;
        this.view = new View();
    }

    public void start() {
        view.printWelcome();

        setupParty();
        gameLoop();
    }

    private void setupParty() {
        view.printPartyCreationPrompt();
        view.printEnterPartyName();
        String partyName = scanner.nextLine();
        party = new Party(0);

        view.printClassSelectionHeader();

        for (int i = 0; i < 3; i++) {
            view.printEnterHeroName(i + 1);
            String name = scanner.nextLine();

            view.printChooseClass(name);
            view.printClassOptions();
            view.printChoicePrompt();
            int choice = Integer.parseInt(scanner.nextLine());

            Model.Character character = switch (choice) {
                case 1 -> new Warrior(name, 120, 1, 15, 5, 8, 4);
                case 2 -> new Mage(name, 80, 1, 10, 2, 50, 15);
                case 3 -> new Healer(name, 90, 1, 8, 3, 20);
                default -> new Warrior(name, 120, 1, 15, 5, 8, 4);
            };

            party.addMember(character);
            view.printHeroJoined(character.getName(), character.getClass().getSimpleName());
        }

        view.printPartyCreated(party.getGold());
        showParty();
    }

    private void gameLoop() {
        while (!party.isPartyDefeated()) {
            view.printEncounterHeader(enemiesDefeated + 1);
            view.printMainMenu();

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                continue;
            }

            switch (choice) {
                case 1 -> startBattle();
                case 2 -> restParty();
                case 3 -> showParty();
                case 4 -> manageInventory();
                case 5 -> {
                    view.printThanksForPlaying();
                    return;
                }
                default -> view.printInvalidChoice();
            }
        }

        view.printGameOver(enemiesDefeated);
    }

    private void startBattle() {
        List<Enemy> enemies = generateEnemies();
        view.printEncountered(enemies);

        BattleSimulator battle = new BattleSimulator(view);
        battle.startBattle(party, enemies, scanner);

        boolean won = !party.isPartyDefeated();
        for (Enemy e : enemies) {
            if (e.getHealth() > 0) {
                won = false;
                break;
            }
        }

        if (won) {
            enemiesDefeated++;
            view.printBattleWon();
            scanner.nextLine();
        }
    }

    private List<Enemy> generateEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        int count = 1 + random.nextInt(3);

        for (int i = 0; i < count; i++) {
            int type = random.nextInt(3);
            switch (type) {
                case 0 -> enemies.add(new Goblin("Goblin " + (i + 1), 40 + enemiesDefeated * 10, 8 + enemiesDefeated * 2, 0.5));
                case 1 -> enemies.add(new Goblin("Goblin Scout " + (i + 1), 30 + enemiesDefeated * 8, 6 + enemiesDefeated * 2, 0.3));
                case 2 -> enemies.add(new Dragon("Dragon " + (i + 1), 60 + enemiesDefeated * 15, 12 + enemiesDefeated * 3, 0.7));
            }
        }
        return enemies;
    }

    private void restParty() {
        if (party.getGold() < 50) {
            view.printNotEnoughGold(50);
            return;
        }

        party.setGold(party.getGold() - 50);
        for (Model.Character c : party.getMembers()) {
            c.setHealth(Math.min(c.getHealth() + 30, c.getMaxHealth()));
            if (c instanceof Mage mage) {
                mage.setMana(mage.getMaxMana());
            }
        }
        view.printRestMessage(party.getGold());
    }

    private void showParty() {
        view.printPartyStatus(party);
    }

    private void manageInventory() {
        view.printInventoryManagement(party);

        System.out.print("\nSelect character (0 to cancel): ");
        int charChoice = Integer.parseInt(scanner.nextLine());

        if (charChoice == 0) return;
        if (charChoice < 1 || charChoice > party.getMembers().size()) {
            view.printInvalidChoice();
            return;
        }

        Model.Character selected = party.getMembers().get(charChoice - 1);
        List<Item> items = selected.getInventory().getItems();

        if (items.isEmpty()) {
            view.printInventoryEmpty();
            return;
        }

        System.out.print("Select item to use (0 to cancel): ");
        int itemChoice = Integer.parseInt(scanner.nextLine());

        if (itemChoice == 0) return;
        if (itemChoice < 1 || itemChoice > items.size()) {
            view.printInvalidChoice();
            return;
        }

        Item selectedItem = items.get(itemChoice - 1);

        if (selectedItem instanceof Potion) {
            System.out.println("Select target for " + selectedItem.getName() + ":");
            for (int i = 0; i < party.getMembers().size(); i++) {
                Model.Character target = party.getMembers().get(i);
                System.out.println((i + 1) + ". " + target.getName() + " (HP: " + target.getHealth() + "/" + target.getMaxHealth() + ")");
            }
            System.out.print("Target (0 to cancel): ");
            int targetChoice = Integer.parseInt(scanner.nextLine());

            if (targetChoice == 0) return;
            if (targetChoice < 1 || targetChoice > party.getMembers().size()) {
                view.printInvalidChoice();
                return;
            }

            Model.Character target = party.getMembers().get(targetChoice - 1);
            selected.getInventory().useItem(selectedItem, target);
            System.out.println("Used " + selectedItem.getName() + " on " + target.getName() + "!");
        } else {
            selected.getInventory().useItem(selectedItem, selected);
            System.out.println("Used " + selectedItem.getName() + "!");
        }
    }
}
