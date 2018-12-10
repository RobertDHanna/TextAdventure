package game.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {

    private String name;
    private String currentAreaID;

    private static final int BASE_HP = 20;
    private static final int BASE_AP = 10;
    private static final int BASE_DMG = 2;

    //Sample experience values; subject to change.
    private static final int LEVEL1 = 0;
    private static final int LEVEL2 = 10;
    private static final int LEVEL3 = 20;
    private static final int LEVEL4 = 40;
    private static final int LEVEL5 = 80;
    private static final int LEVEL6 = 160;
    private static final int LEVEL7 = 320;
    private static final int LEVEL8 = 640;
    private static final int LEVEL9 = 1280;
    private static final int LEVEL10 = 2560;

    private int[] levels;

    private int max_hp;
    private int current_hp;
    private int max_ap;
    private int current_ap;

    private int experience; //Experience points; will go up when defeating enemies or possibly when performing certain actions.
    int current_level;
    int next_level;

    //Note: We may want to wait to implement exp and leveling up until after the first demo.

    private int strength; //Affects how much damage you deal at a time (still need to figure out calculations).
    private int dexterity; //Affects your chance to hit enemies and the chance of enemies to hit you (still need to figure out calculations).
    private int intelligence; //Determines your max ability points, which determines how often you can use special abilities; max_ap = BASE_AP + 2x intelligence.
    private int constitution; //Determines your max health; max_hp = BASE_HP + 4x constitution.

    //These bonuses come from items and will be recalculated every time an item is equipped or unequipped.
    private int bonus_str;
    private int bonus_dex;
    private int bonus_int;
    private int bonus_con;

    private List<Equipment> equippedItems;
    private List<Item> inventory;
    private List<String> defeatedEnemies;

    public Player() {

        name = "Player 1";



        //Starting values; if we implement allocating stat points at the beginning of the game, these can be changed.
        strength = 1;
        dexterity = 1;
        intelligence = 1;
        constitution = 1;

        bonus_str = 0;
        bonus_dex = 0;
        bonus_int = 0;
        bonus_con = 0;

        max_hp = BASE_HP + (4 * constitution);
        current_hp = max_hp;
        max_ap = BASE_AP + (2 * intelligence);
        current_ap = max_ap;

        levels = new int[11];
        levels[0] = 0;
        levels[1] = LEVEL1;
        levels[2] = LEVEL2;
        levels[3] = LEVEL3;
        levels[4] = LEVEL4;
        levels[5] = LEVEL5;
        levels[6] = LEVEL6;
        levels[7] = LEVEL7;
        levels[8] = LEVEL8;
        levels[9] = LEVEL9;
        levels[10] = LEVEL10;

        equippedItems = new ArrayList<>();
        inventory = new ArrayList<>();
        defeatedEnemies = new ArrayList<>();
        experience = 0;
        next_level = 2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void equipItem(Equipment item) {
        if (!inventory.contains(item)) {
            System.out.print("You don't have that item!"); //may change this later
        } else {
            inventory.remove(item);
            equippedItems.add(item);

            calculateStats();
        }
    }

    private void resetBonusStats() {
        bonus_str = 0;
        bonus_dex = 0;
        bonus_int = 0;
        bonus_con = 0;
    }

    private void calculateStats() {

        resetBonusStats();

        for (Equipment e : equippedItems) {
            List<Equipment.Bonus> bonuses = e.getBonuses();
            for (Equipment.Bonus bonus : bonuses) {
                switch (bonus) {
                    case STR:
                        bonus_str += 1;
                        break;
                    case DEX:
                        bonus_dex += 1;
                        break;
                    case INT:
                        bonus_int += 1;
                        break;
                    case CON:
                        bonus_con += 1;
                        break;
                    default:
                        break;
                }
            }
        }

        calculateHpAndAp();
    }

    private void calculateHpAndAp () {

        max_hp = BASE_HP + (4 * (constitution + bonus_con));
        max_ap = BASE_AP + (2 * (intelligence + bonus_int));

        //Currently, the player will always be at full health outside of battle. If this changes later,
        // we can put stuff to calculate new values for current_hp and current_ap here.
        current_hp = max_hp;
        current_ap = max_ap;
    }

    public int getHitDamage() {
        return BASE_DMG + strength + bonus_str;
    }

    public int getMaxHp() {
        return max_hp;
    }

    public int getCurrentHp() {
        return current_hp;
    }

    public int getMaxAp() {
        return max_ap;
    }

    public int getCurrentAp() {
        return current_ap;
    }

    public int getDexterity() {
        return dexterity;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public HashMap<String, Item> getInventoryAsMap() {
        HashMap<String, Item> map = new HashMap<>();
        for (Item item : inventory) {
            map.put(item.getId(), item);
        }
        return map;
    }

    public void takeDamage(int damage) {
        current_hp = current_hp - damage;
    }

    public void resetHp() {
        current_hp = max_hp;
    }

    public SaveData save() {
        SaveData data = new SaveData();

        data.setBonus_con(bonus_con);
        data.setBonus_dex(bonus_dex);
        data.setBonus_int(bonus_int);
        data.setBonus_str(bonus_str);
        data.setConstitution(constitution);
        data.setCurrent_ap(current_ap);
        data.setCurrent_hp(current_hp);
        data.setDexterity(dexterity);
        data.setExp(experience);
        data.setIntelligence(intelligence);
        data.setMax_ap(max_ap);
        data.setMax_hp(max_hp);
        data.setStrength(strength);

        return data;
    }

    public void addDefeatedEnemy(String areaIdPlusEnemyId) {
        defeatedEnemies.add(areaIdPlusEnemyId.trim().toLowerCase());
    }

    public Boolean hasAlreadyDefeatedEnemy(String areaIdPlusEnemyId) {
        return defeatedEnemies.contains(areaIdPlusEnemyId.trim().toLowerCase());
    }

    public void gainExperience(int experience) {
        this.experience += experience;
        while(this.experience > levels[next_level]) {
            System.out.println("Congratulations! You just went up a level!\n");
            levelUp();
            next_level++;
        }
    }

    public void levelUp() {
        System.out.println("You have 5 stat points.\n");
        int remaining_points = 5;
        int points_to_str = 0;
        int points_to_dex = 0;
        int points_to_con = 0;
        int points_to_int = 0;

        boolean finished = false;
        while (!finished) {
            System.out.println("Please choose how to distribute your stat points:\n");
            System.out.println(String.format("1. Strength: %d + %d", this.strength, points_to_str));
            System.out.println(String.format("2. Dexterity: %d + %d", this.dexterity, points_to_dex));
            System.out.println(String.format("3. Constitution: %d + %d", this.constitution, points_to_con));
            System.out.println(String.format("4. Intelligence: %d + %d", this.intelligence, points_to_int));
            System.out.println(String.format("(Points Remaining: %d)\n", remaining_points));
            System.out.println("Type 'help' for more info, 'done' when finished, or 'reset' to start over.");
            System.out.print(">>: ");

            String input = getTrimmedLowerCaseInput();
            System.out.println();

            int points = 0;
            switch(input) {
                case "1":
                case "str":
                case "strength":
                    points = getPointChoice("strength");
                    if (points > remaining_points) {
                        System.out.println("You don't have enough points left!\n");
                        break;
                    } else {
                        points_to_str += points;
                        remaining_points -= points;
                    }
                    break;
                case "2":
                case "dex":
                case "dexterity":
                    points = getPointChoice("dexterity");
                    if (points > remaining_points) {
                        System.out.println("You don't have enough points left!\n");
                        break;
                    } else {
                        points_to_dex += points;
                        remaining_points -= points;
                    }
                    break;
                case "3":
                case "con":
                case "constitution":
                    points = getPointChoice("constitution");
                    if (points > remaining_points) {
                        System.out.println("You don't have enough points left!\n");
                        break;
                    } else {
                        points_to_con += points;
                        remaining_points -= points;
                    }
                    break;
                case "4":
                case "int":
                case "intelligence":
                    points = getPointChoice("intelligence");
                    if (points > remaining_points) {
                        System.out.println("You don't have enough points left!\n");
                        break;
                    } else {
                        points_to_int += points;
                        remaining_points -= points;
                    }
                    break;
                case "help":
                    System.out.println("Strength affects how much damage you deal when hitting an enemy.");
                    System.out.println("Dexterity affects your chance to hit an enemy each turn.");
                    System.out.println("Constitution affects how many life points you have.");
                    System.out.println("Intelligence affects how many ability points you have.\n");
                    break;
                case "done":
                    if (remaining_points > 0) {
                        System.out.println("Are you sure? You still have stat points remaining that will be lost. (yes/no)");
                        boolean validInput = false;
                        while (!validInput) {
                            System.out.print(">>: ");
                            String confirmation = getTrimmedLowerCaseInput();
                            System.out.println();
                            if (confirmation.equals("y") || confirmation.equals("yes")) {
                                validInput = true;
                                finished = true;
                            } else if (confirmation.equals("n") || confirmation.equals("no")) {
                                validInput = true;
                                break;
                            } else {
                                System.out.println("That isn't an option!\n");
                            }
                        }
                    } else {
                        finished = true;
                        break;
                    }
                case "reset":
                    remaining_points = 5;
                    points_to_str = 0;
                    points_to_dex = 0;
                    points_to_con = 0;
                    points_to_int = 0;
                    break;
                default:
                    System.out.println("I don't recognize that option.\n");
            }

        }

        this.strength += points_to_str;
        this.dexterity += points_to_dex;
        this.constitution += points_to_con;
        this.intelligence += points_to_int;
    }

    private int getPointChoice(String stat) {
        System.out.println(String.format("How many points would you like to put into %s?\n", stat));
        System.out.print(">>: ");
        String input = getTrimmedLowerCaseInput();

        int points = 0;
        try {
            points = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("That's not a number!\n");
            return 0;
        }
        if (points < 0) {
            System.out.println("You can't choose a negative number!\n");
            return 0;
        }
        return points;
    }

    private String getTrimmedLowerCaseInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return input.trim().toLowerCase();
    }

    public void setCurrentAreaID(String id) {
        currentAreaID = id;
    }
}
