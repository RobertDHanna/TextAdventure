package game.model;

import java.util.ArrayList;
import java.util.List;

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

    String getId()
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
                if (inputList.get(1) != null && inputList.get(1).equals("to")) {
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
                if (inputList.size() != 2) {
                    World.print(String.format("Incorrect number of arguments: %d\n" +
                                    "Usage: %s", inputList.size(), Help.PICKUP_USAGE));
                }
                    handlePickup(inputList.get(1));
                    break;
            case "help":
                Help.handleHelp();
                break;
        }
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
            World.print(myItem.getDescription());
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

    private void handlePickup(String trigger) {
        String itemStr = getItemIdByTrigger(trigger);
        Item myItem = world.getItemById(itemStr);
        if(myItem == null){
            World.print(String.format("No such item \'%s\' in this area\n",trigger));
        }
        else if(myItem.getDescription().isEmpty()){
            World.print("There seems to be no description for this object\n");
        }
        else{
            world.getPlayer().addToInventory(myItem);
            World.print(String.format("%s picked up!",myItem.getId()));
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

}

