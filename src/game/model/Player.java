package game.model;

import java.util.List;

public class Player {

    private int max_hp;
    private int current_hp;
    private int max_ap;
    private int current_ap;

    private int strength; //affects how much damage you deal at a time (still need to figure out calculations)
    private int dexterity; //affects your chance to hit enemies and the chance of enemies to hit you (still need to figure out calculations)
    private int intelligence; //determines your max ability points, which determines how often you can use special abilities; max_ap = 2x intelligence
    private int constitution; //determines your max health; max_hp = 4x constitution

    List<Equipment> equippedItems;
    List<Item> inventory;

    void addToInventory(Item item) {
        inventory.add(item);
    }
}
