package game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {

    private static final int BASE_HP = 20;
    private static final int BASE_AP = 10;
    private static final int BASE_DMG = 3;

    //Sample experience values; subject to change.
    private static final int LEVEL1 = 10;
    private static final int LEVEL2 = 20;
    private static final int LEVEL3 = 40;
    private static final int LEVEL4 = 80;
    private static final int LEVEL5 = 160;
    private static final int LEVEL6 = 320;
    private static final int LEVEL7 = 640;
    private static final int LEVEL8 = 1280;
    private static final int LEVEL9 = 2560;
    private static final int LEVEL10 = 5120;

    private int max_hp;
    private int current_hp;
    private int max_ap;
    private int current_ap;
    private int exp; //Experience points; will go up when defeating enemies or possibly when performing certain actions.

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

    public Player() {

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

        equippedItems = new ArrayList<>();
        inventory = new ArrayList<>();
    }

    void addToInventory(Item item) {
        inventory.add(item);
    }

    void equipItem(Equipment item) {
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
}
