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

    public String getId()
    {
        return id;
    }

    public String getName() { return name; }

    public String getDescription()
    {
        return description;
    }

    public String getInRoomDescription() { return inRoomDescription; }

    public Boolean getPickupable()
    {
        return pickupable;
    }

    public List<String> getActionTriggers()
    {
        return actionTriggers;
    }
}
