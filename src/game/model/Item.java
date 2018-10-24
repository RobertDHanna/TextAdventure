package game.model;

import java.util.List;

public class Item
{
    private String id;
    private String description;
    private Boolean pickupable;
    private List<String> action_triggers;

    public Item(String id, String description, Boolean pickupable, List<String> actionTriggers)
    {
        this.id = id;
        this.description = description;
        this.pickupable = pickupable;
        this.action_triggers = actionTriggers;
    }

    String getId()
    {
        return id;
    }

    String getDescription()
    {
        return description;
    }

    public Boolean getPickupable()
    {
        return pickupable;
    }

    List<String> getAction_triggers()
    {
        return action_triggers;
    }
}
