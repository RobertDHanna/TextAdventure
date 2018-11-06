package game.model;

import java.util.List;

public class Item
{
    private String id;
    private String name;
    private String description;
    private Boolean pickupable;
    private String inRoomDescription;
    private List<String> actionTriggers;

    public Item(String id, String name, String description, Boolean pickupable, String inRoomDescription, List<String> actionTriggers)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pickupable = pickupable;
        this.inRoomDescription = inRoomDescription;
        this.actionTriggers = actionTriggers;
    }

    String getId()
    {
        return id;
    }

    String getName() { return name; }

    String getDescription()
    {
        return description;
    }

    String getInRoomDescription() { return inRoomDescription; }

    public Boolean getPickupable()
    {
        return pickupable;
    }

    List<String> getActionTriggers()
    {
        return actionTriggers;
    }
}
