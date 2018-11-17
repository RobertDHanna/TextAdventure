package game.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemList {
    List<Item> items;
    /**A mapping of item IDs to their triggers,making getting the triggers faster/easier*/
    private Map<String,List<String>> idToListOfTriggers;

    public ItemList(List<Item> items) {
        this.items = items;
    }
    /*Setup member vars for ItemList class**/
    void setupItemList() {
        idToListOfTriggers = new HashMap<>();
        for (Item item : this.items) {
            idToListOfTriggers.putIfAbsent(item.getId(),item.getActionTriggers());
        }
    }

    /*Lets you get a specific item by its id
     * @param id The id to get the item by
     * @return Item object that corresponds to the id
     * **/
    public Item getItem(String id){
        for (Item item : items) {
            if (item.getId().toLowerCase().equals(id.toLowerCase())) {
                return item;
            }
        }
        return null;
    }
    /*Finds an item's ID that corresponds to a given action trigger
    * @param trigger the string which is supposed to be a trigger
    * @param areaItemIDS A List of item ids associated with a given area
    * @return String The ID of item is returned if found, else empty string**/
    String findIDByTrigger(String trigger,List<String> areaItemIDS){
        for (String id : areaItemIDS) {
            //get the action triggers that go with this id and see if trigger is in there
            List<String> triggers = idToListOfTriggers.get(id);
            if (triggers.contains(trigger)) {
                return id;
            }
        }
        return "";
    }

    public List<Item> getItems() {
        return items;
    }

}
