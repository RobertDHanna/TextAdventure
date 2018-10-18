package Game.model;

import java.util.HashMap;
import java.util.List;

public class World
{
    private Map map;
    private Player player;
    private String currPlayerAreaId;
    private HashMap<String, Area> areaIdToArea;
    private HashMap<String, Item> itemIdToItem;
    private HashMap<String, List<Connection>> areaIdToConnection;

    public World(Map map, Player player, String currPlayerAreaId, HashMap<String, Area> areaIdToArea, HashMap<String, Item> itemIdToItem, HashMap<String, List<Connection>> areaIdToConnection)
    {
        this.map = map;
        this.player = player;
        this.currPlayerAreaId = currPlayerAreaId;
        this.areaIdToArea = areaIdToArea;
        this.itemIdToItem = itemIdToItem;
        this.areaIdToConnection = areaIdToConnection;
    }

    public void handleAction(String action) {
        // turn into List<String>
    }

    public void handlePlayerMove(String AreaId) {

    }
}
