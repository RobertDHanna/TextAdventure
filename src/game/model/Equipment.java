package game.model;

import java.util.List;

public class Equipment extends Item {

    public Equipment(String id, String name, String description, Boolean pickupable, String inRoomDescription, List<String> actionTriggers) {
        super(id, name, description, pickupable, inRoomDescription, actionTriggers);
    }

    public enum Bonus {
        STR, DEX, INT, CON
    }
    private List<Bonus> bonuses;

    public enum Slot {
        HEAD, TORSO, ARMS, HANDS, RING, LEGS, FEET
    }
    private Slot slot;

    List<Bonus> getBonuses() {
        return bonuses;
    }

    Slot getSlot() {
        return slot;
    }
}
