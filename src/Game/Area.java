package Game;

import Game.Item;

import java.util.List;

public class Area
{
    private String id;
    private String name;
    private String description;
    private List<Item> items;

    public Area(String id, String name, String description, List<Item> items)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public List<Item> getItems()
    {
        return items;
    }
}
