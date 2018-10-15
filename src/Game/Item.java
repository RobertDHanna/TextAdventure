package Game;

import java.util.List;

public class Item
{
    private String id;
    private String description;
    private Boolean pickupable;
    private List<String> actionTriggers;

    public Item(String id, String description, Boolean pickupable, List<String> actionTriggers)
    {
        this.id = id;
        this.description = description;
        this.pickupable = pickupable;
        this.actionTriggers = actionTriggers;
    }

    public String getId()
    {
        return id;
    }

    public String getDescription()
    {
        return description;
    }

    public Boolean getPickupable()
    {
        return pickupable;
    }

    public List<String> getActionTriggers()
    {
        return actionTriggers;
    }
}
