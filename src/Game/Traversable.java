package Game;

import java.util.List;

public class Traversable
{
    private String toAreaId;
    private String traversableName;
    private String description;
    private List<String> requiredItemIds;

    public Traversable(String toAreaId, String traversableName, String description, List<String> requiredItemIds)
    {
        this.toAreaId = toAreaId;
        this.traversableName = traversableName;
        this.description = description;
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

    public List<String> getRequiredItemIds()
    {
        return requiredItemIds;
    }
}
