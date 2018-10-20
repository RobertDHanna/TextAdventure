package game.model;

import java.util.List;

public class Connection
{
    private String areaId;
    private List<Traversable> traversables;

    public Connection(String areaId, List<Traversable> traversables)
    {
        this.areaId = areaId;
        this.traversables = traversables;
    }

    public String getAreaId()
    {
        return areaId;
    }

    public List<Traversable> getTraversables()
    {
        return traversables;
    }
}
