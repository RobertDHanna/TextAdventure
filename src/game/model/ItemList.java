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
            idToListOfTriggers.putIfAbsent(item.getId(),item.getAction_triggers());
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
    //TODO could be simplified if triggers were mapped to ids as well, but does it really matter?
    /*Finds an item's ID by a given trigger
    * @param trigger the string which is supposed to be a trigger
    * @return String The ID of item is returned if found, else empty string**/
    String findIDByTrigger(String trigger){
        for (Map.Entry<String, List<String>> entry : idToListOfTriggers.entrySet())
        {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            if (entry.getValue() != null && entry.getValue().contains(trigger)){
                return entry.getKey();
            }
        }
        return "";
    }

}
