package game.model;

import com.google.gson.Gson;

import game.Battle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Area
{
    private String id;
    private String name;
    private String description;
    private List<String> itemIds;
    private World world;

    public Area(String id, String name, String description, List<String> itemIds, World world)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemIds = itemIds;
        this.world = world;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    String getDescription()
    {
        return description;
    }

    public List<String> getItemIds()
    {
        return itemIds;
    }

    void handleAction(List<String> inputList) {
        if (inputList.isEmpty()){
            return;
        }
        switch (inputList.get(0).toLowerCase()) {
            case "go":
                if (inputList.size() > 1 && inputList.get(1).equals("to")) {
                    inputList.remove(1);
                }
                // intentional fallthrough
            case "goto":
                if (inputList.size() < 2){
                    World.print(String.format("Incorrect number of arguments: %d\n" +
                                    "Usage: %s\n", inputList.size(), Help.GO_USAGE));
                    break;
                }
                inputList.remove(0);
                handleGo(String.join(" ", inputList));
                break;
            case "inspect":
            case "i":
                //World.print("Inspect command recognized.\n");
                if (inputList.size() != 2) {
                    World.print(String.format("Incorrect number of arguments: %d\n" +
                                              "Usage: %s\n",inputList.size(), Help.INSPECT_USAGE));
                    break;
                }
                handleInspect(inputList.get(1));
                break;
            case "look":
            case "l":
                handleLook();
                break;
            case "pickup":
            case "take":
                if (inputList.size() != 2) {
                    World.print(String.format("Incorrect number of arguments: %d\n" +
                                    "Usage: %s\n", inputList.size(), Help.PICKUP_USAGE));
                    break;
                }
                handlePickup(inputList.get(1));
                break;
            case "help":
                Help.handleHelp();
                break;
            case "inventory":
            case "inv":
                if (inputList.size() != 1) {
                    World.print(String.format("Incorrect number of arguments: %d\n" +
                            "Usage: %s", inputList.size(), Help.INVENTORY));
                }
                handleInventory();
                break;
            case "battle":
                Enemy goblin = new Enemy("goblin","Goblin", "description", 10, 1, 2, 4, 0, null);
                Battle battle = new Battle();
                Battle.BattleResult battleResult = battle.run(world.getPlayer(), goblin);
                handleBattleResult(battleResult);
                break;
            case "save":
                try {
                    handleSave();
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                this.printUnknownCommand();
        }
    }

    public void handleEnterArea() {
        // loop through enemies that haven't been defeated yet
        // spawn a battle instance with that enemy

        // Battle battle = new Battle();
        // Battle.BattleResult battleResult = battle.run(world.getPlayer(), <ENEMY INSTANCE>);
        // this.handleBattleResult(battleResult)
    }

    private void handleBattleResult(Battle.BattleResult battleResult) {
        switch (battleResult) {
            case PLAYER_WON:
                // handle player win
                break;
            case PLAYER_LOST:
                // handle player loss
                break;
            case PLAYER_RAN:
                // handle craven player
                break;
        }
    }

    private void printUnknownCommand() {
        World.print("I don't understand what you mean...", "\n");
    }

    private void handleInventory() {
        Player player = world.getPlayer();
        StringBuilder answer = new StringBuilder("YOUR INVENTORY:\n");
        for ( Item item : player.getInventory()){
            answer.append(String.format("%s - %s\n",item.getName(), item.getDescription()));
        }
        if (player.getInventory().size() == 0){
            answer.append("Is Empty!\n");
        }

        World.print(answer.toString());
    }

    /*This method verifies that the string passed in is an action trigger for an item in the current room and
    * then returns that item id
    * @param trigger The string that might correspond to an item's action trigger
    * @param areaITemIDS The list of item ids for this area
    * @return String The id of item whose trigger was passed in
    * **/
    private String getItemIdByTrigger(String trigger) {
        return world.getItemIdByTrigger(trigger.toLowerCase());
    }


    private void handleInspect(String trigger) {
        String itemStr = getItemIdByTrigger(trigger);
        Item myItem = world.getItemById(itemStr);

        if(myItem == null){
            World.print(String.format("No such item \'%s\' in this area\n",trigger));
        }
        else if(myItem.getDescription().isEmpty()){
            World.print("There seems to be no description for this object\n");
        }
        else {
            World.print(myItem.getDescription(), "\n");
        }
    }

    private void handleGo(String traversableName) {
        traversableName = traversableName.toLowerCase();
        List<Traversable> traversables = world.getTraversables(id);
        for (Traversable traversable : traversables) {
            String lowerCaseName = traversable.getTraversableName().toLowerCase();
            if (traversableName.equals(lowerCaseName)) {
                world.goToArea(traversable);
                return;
            }
        }
        world.print("I'm not sure where that is...\n");
    }

    private boolean isItemInArea(Item item) {
        for(String itemId : itemIds) {
            if(Objects.equals(item.getId(), itemId)) {
                return true;
            }
        }
        return false;
    }

    private void handlePickup(String trigger) {
        String itemStr = getItemIdByTrigger(trigger);
        Item myItem = world.getItemById(itemStr);
        if(myItem == null || !isItemInArea(myItem)){
            World.print(String.format("No such item \'%s\' in this area\n",trigger));
        }
        else if(myItem.getDescription().isEmpty()){
            World.print("There seems to be no description for this object\n");
        } else if(!myItem.getPickupable()) {
            World.print(String.format("You can't pick up %s!", myItem.getName()), "\n");
        } else {
            world.getPlayer().addToInventory(myItem);

            for(int i = 0; i < itemIds.size(); i++) {
                String itemId = itemIds.get(i);
                if(Objects.equals(itemId, myItem.getId())) {
                    itemIds.remove(i);
                    break;
                }
            }
            World.print(String.format("%s picked up!\n",myItem.getName()));
        }

    }


    private void handleLook() {
        world.describeArea(this);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (String itemId : itemIds) {
            items.add(world.getItemById(itemId));
        }
        return items;
    }

    private void handleSave() throws IOException {
        SaveData data = world.getPlayer().save();
        data.setAreaID(id);

        Gson gson = new Gson();
        String json = gson.toJson(data);


        try (Writer writer = new FileWriter("save.json")){
            writer.write(json);
        } catch (IOException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

}

