package game.model;

import java.util.List;

public class SaveData {
    private int max_hp;
    private int current_hp;
    private int max_ap;
    private int current_ap;
    private int exp;

    private int strength;
    private int dexterity;
    private int intelligence;
    private int constitution;

    private int bonus_str;
    private int bonus_dex;
    private int bonus_int;
    private int bonus_con;

    private Traversable traversable;
    private List<String> defeatedEnemies;

    public SaveData() {

    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public int getCurrent_hp() {
        return current_hp;
    }

    public void setCurrent_hp(int current_hp) {
        this.current_hp = current_hp;
    }

    public int getMax_ap() {
        return max_ap;
    }

    public void setMax_ap(int max_ap) {
        this.max_ap = max_ap;
    }

    public int getCurrent_ap() {
        return current_ap;
    }

    public void setCurrent_ap(int current_ap) {
        this.current_ap = current_ap;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getBonus_str() {
        return bonus_str;
    }

    public void setBonus_str(int bonus_str) {
        this.bonus_str = bonus_str;
    }

    public int getBonus_dex() {
        return bonus_dex;
    }

    public void setBonus_dex(int bonus_dex) {
        this.bonus_dex = bonus_dex;
    }

    public int getBonus_int() {
        return bonus_int;
    }

    public void setBonus_int(int bonus_int) {
        this.bonus_int = bonus_int;
    }

    public int getBonus_con() {
        return bonus_con;
    }

    public void setBonus_con(int bonus_con) {
        this.bonus_con = bonus_con;
    }

    public Traversable getTraversable() {
        return traversable;
    }

    public void setTraversable(Traversable traversable) {
        this.traversable = traversable;
    }

    public void setDefeated(List<String> defeatedEnemies) {
        this.defeatedEnemies = defeatedEnemies;
    }

    public List<String> getDefeated() {
        return this.defeatedEnemies;
    }
}
