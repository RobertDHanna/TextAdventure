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
                if (inputList.size() < 2){
                    World.print(String.format("Incorrect number of arguments: %d\n" +
                                    "Usage |go| |\'area\'|\n",inputList.size()));
                    break;
                }
                inputList.remove(0);
                handleGo(String.join(" ", inputList));
                break;
            case "inspect":
                //World.print("Inspect command recognized.\n");
                if (inputList.size() != 2){
                    World.print(String.format("Incorrect number of arguments: %d\n" +
                                              "Usage |inspect| |\'item\'|\n",inputList.size()));
                    break;
                }
                handleInspect(inputList.get(1));
                break;
            case "look":
                handleLook();
                break;
        }
    }
    /*This method verifies that the string passed in is an action trigger for an item in the current room and
    * then returns that item id
    * @param trigger The string that might correspond to an item's action trigger
    * @param areaITemIDS The list of item ids for this area
    * @return String The id of item whose trigger was passed in
    * **/
    private String verifyTriggers(String trigger,List<String> areaItemIDS) {
        return Model.getInstance().stringIsTrigger(trigger.toLowerCase(),areaItemIDS);
    }


    private void handleInspect(String trigger) {
        String itemStr = verifyTriggers(trigger,getItemIds());
        Item myItem = Model.getInstance().getItem(itemStr);

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
            items.add(Model.getInstance().getItem(itemId));
        }
        return items;
    }
}
