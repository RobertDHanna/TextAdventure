package game.model;

import java.util.List;

public class ItemList {
    List<Item> items;

    public ItemList(List<Item> items) {
        this.items = items;
    }

    /*Lets you get a specific item by its id
     * @param id The id to get the item by
     * @return Item object that corresponds to the id
     * **/
    public Item getItem(String id){
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }


}
