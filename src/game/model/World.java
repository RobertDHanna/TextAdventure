package game.model;

import java.util.*;
import java.util.stream.Collectors;

public class World
{
    private GameMap map;
    private Player player;
    private EnemyList enemyList;
    private String currPlayerAreaId;
    private Map<String, Area> areaIdToArea;
    private Map<String, Item> itemIdToItem;
    private Map<String, List<Traversable>> areaIdToTraversables;

    public World(IModel model, Player player)
    {
        this.map = model.getMap();
        this.enemyList = model.getEnemyList();
        this.player = player;
        this.currPlayerAreaId = this.map.getStartAreaId();

        Map<String, Area> areaIdToArea = new HashMap<>();
        Map<String, List<Traversable>> areaIdToTraversables = new HashMap<>();
        for(Connection connection : this.map.getConnectionList()) {
            Area area = model.getListOfAreas().getArea(connection.getAreaId());
            area.setWorld(this);
            areaIdToArea.put(connection.getAreaId(), area);
            areaIdToTraversables.put(connection.getAreaId(), connection.getTraversables());
        }

        this.areaIdToArea = areaIdToArea;
        this.areaIdToTraversables = areaIdToTraversables;

        Map<String, Item> itemIdToItem = new HashMap<>();
        for(Item item : model.getItemList().items) {
            itemIdToItem.put(item.getId(), item);
        }
        this.itemIdToItem = itemIdToItem;
    }

    public void handleInput(String input) {
        List<String> actionList = new ArrayList<>(Arrays.asList(input.split("\\s+")))
                .stream()
                .filter(action -> action.length() > 0)
                .map(action -> action.trim())
                .collect(Collectors.toList());
        areaIdToArea.get(this.currPlayerAreaId).handleAction(actionList);
    }

    public String getCurrentPlayerAreaDescription() {
        return areaIdToArea.get(this.currPlayerAreaId).getDescription();
    }

    public void printStartingWorldDialog() {
        World.print("You wake up in your bedroom at the start of another perfectly normal day.  Or is it...?  You have a strange feeling that something isn't right, but you push it away.  You get up out of bed and stretch.  You are already fully clothed for some reason, but that doesn't seem to bother you.  Time to see what this day has in store!","\n",
                "\n",
                "Welcome to $GameNameHere! Type 'help' at anytime for a list of available commands.", "\n\n");
    }

    public static void print(String... printables) {
        for (String printable : printables) {
            System.out.print(printable);
        }
    }

    public List<Traversable> getTraversables(String areaId) {
        return areaIdToTraversables.get(areaId);
    }

    public void goToArea(Traversable traversable) {
        List<String> requiredItemIds = traversable.getRequiredItemIds();
        HashMap<String, Item> playerInventoryMap = player.getInventoryAsMap();
        for (String itemId : requiredItemIds) {
            if (!playerInventoryMap.containsKey(itemId)) {
                World.print("\n", "You do not have the required item(s) to continue!","\n");
                return;
            }
            World.print("\n", "Used ", playerInventoryMap.get(itemId).getName(), ".","\n");
        }
        World.print("\n", traversable.getTraversableActionString(), "\n");
        currPlayerAreaId = traversable.getToAreaId();
        Area area = areaIdToArea.get(currPlayerAreaId);
        describeArea(area);
        area.handleEnterArea();
    }

    public void describeArea(Area area) {
        World.print("\n**************************************************************\n");
        World.print("\n", area.getName(),"\n");
        World.print("\n", area.getDescription());
        List<Item> items = area.getItems();
        HashMap<String, Item> playerInventoryMap = player.getInventoryAsMap();
        for (Item item : items) {
            if (!playerInventoryMap.containsKey(item.getId())) {
                World.print(" ", item.getInRoomDescription());
            }
        }
        World.print("\n");
        for (Traversable traversable : getTraversables(area.getId())) {
            World.print(traversable.getInRoomDescription(), " ");
        }
        World.print("\n");
        World.print("\n**************************************************************\n");
    }

    public Player getPlayer() {
        return player;
    }

    private String getItemIdByTrigger(String trigger, List<Item> items) {
        for(Item i : items) {
            if (i.getActionTriggers().contains(trigger)) {
                return i.getId();
            }
        }
        return null;
    }

    public String getItemIdByTrigger(String trigger) {
        String id = getItemIdByTrigger(trigger, areaIdToArea.get(currPlayerAreaId).getItems());
        if(id == null) {
            id = getItemIdByTrigger(trigger, player.getInventory());
        }
        return id;
    }

    public Item getItemById(String id) {
        return itemIdToItem.get(id);
    }

    public Enemy getEnemyById(String id) {return enemyList.getEnemy(id);}
}
