package game.model;

import java.util.List;

public class Traversable
{
    private String toAreaId;
    private String traversableName;
    private String description;
    private String traversableActionString;
    private String inRoomDescription;
    private List<String> requiredItemIds;

    public Traversable(String toAreaId, String traversableName, String description, String traversableActionString, String inRoomDescription, List<String> requiredItemIds)
    {
        this.toAreaId = toAreaId;
        this.traversableName = traversableName;
        this.description = description;
        this.traversableActionString = traversableActionString;
        this.inRoomDescription = inRoomDescription;
        this.requiredItemIds = requiredItemIds;
    }

    public String getToAreaId()
    {
        return toAreaId;
    }

    public String getTraversableName()
    {
        return traversableName;
    }

    public String getDescription()
    {
        return description;
    }

    public String getTraversableActionString() { return traversableActionString; }

    public String getInRoomDescription() { return inRoomDescription; }

    public List<String> getRequiredItemIds()
    {
        return requiredItemIds;
    }
}
