package game.model;

public class Enemy {

    private int max_hp;
    private int current_hp;

    //min_hit and max_hit determine how much damage the enemy can deal in one hit;
    // if the enemy gets a successful hit, a number will be picked between the two values
    // to determine the damage dealt
    private int min_hit;
    private int max_hit;

    //the enemy's dexterity will be checked against the players; the hit chance of
    // either side will be determined by the difference between the two
    private int dexterity;

    //Note: Standard enemies will likely have either average damage and average dexterity;
    // high dmg, low dex (like something big and slow, or wearing heavy armor);
    // or low dmg, high dex (like something small and weak but really fast).
    // Bosses will have a higher-than-average value for both.

}
