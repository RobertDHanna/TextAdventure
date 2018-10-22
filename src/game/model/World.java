package game.model;

import java.util.*;
import java.util.stream.Collectors;

public class World
{
    private GameMap map;
    private Player player;
    private String currPlayerAreaId;
    private Map<String, Area> areaIdToArea;
    private Map<String, Item> itemIdToItem;
    private Map<String, List<Traversable>> areaIdToTraversables;

    public World(Model model)
    {
        this.map = model.getMap();
        this.player = new Player();
        this.currPlayerAreaId = this.map.getStartAreaId();

        Map<String, Area> areaIdToArea = new HashMap<>();
        Map<String, List<Traversable>> areaIdToTraversables = new HashMap<>();
        for(Connection connection : this.map.getConnectionList()) {
            areaIdToArea.put(connection.getAreaId(), model.getListOfAreas().getArea(connection.getAreaId()));
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
                .stream().filter(action -> action.length() > 0)
                .map(action -> action.trim())
                .collect(Collectors.toList());
        areaIdToArea.get(this.currPlayerAreaId).handleAction(actionList);
    }

    public void handlePlayerMove(String AreaId) {

    }

    public String getCurrentPlayerAreaDescription() {
        return areaIdToArea.get(this.currPlayerAreaId).getDescription();
    }

    public String getStartingWorldDialog() {
        return "Welcome to the game!\n";
    }

    public static void print(String... printables) {
        for (String printable : printables) {
            System.out.print(printable);
        }
    }
}
