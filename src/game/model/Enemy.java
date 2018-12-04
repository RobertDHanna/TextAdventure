package game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy {
    private String id;
    private String name; //Example usage: "You start the fight with the [name]!"
    private String battle_description;
    private int max_hp;
    private int current_hp;

    //The enemy's dexterity will be checked against the players; the hit chance of
    // either side will be determined by the difference between the two.
    private int dexterity;

    //min_hit and max_hit determine how much damage the enemy can deal in one hit;
    // if the enemy gets a successful hit, a number will be picked between the two values
    // to determine the damage dealt.
    private int min_hit;
    private int max_hit;

    //Note: Standard enemies will likely have either average damage and average dexterity;
    // high dmg, low dex (like something big and slow, or wearing heavy armor);
    // or low dmg, high dex (like something small and weak but really fast).
    // Bosses will have a higher-than-average value for both.

    //Items and experience that the enemy drops when defeated. (Not implemented yet)
    private List<String> drops;
    private int exp = 0;

    public Enemy(String id, String name, String battle_description, int hp, int dex, int min_hit, int max_hit, int exp, List<String> drops) {
        this.id = id;
        this.name = name;
        this.battle_description = battle_description;
        this.max_hp = hp;
        this.current_hp = hp;
        this.dexterity = dex;
        this.min_hit = min_hit;
        this.max_hit = max_hit;
        this.exp = exp;
        this.drops = drops;
    }

    public String getId() {
        return id;
    }

    //This method returns a number between min_hit and max_hit
    public int getHitDamage() {
        return ThreadLocalRandom.current().nextInt(min_hit, max_hit + 1);
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return max_hp;
    }

    public int getCurrentHp() {
        return current_hp;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void takeDamage(int damage) {
        current_hp = current_hp - damage;
    }

    public void resetHp() {
        current_hp = max_hp;
    }
}
