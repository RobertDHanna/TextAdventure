package game.model;

import game.encode.Encoder;

import java.io.IOException;

/*
*
* This class holds all the data for the game. When the game starts up the model is created
*
*
 */

public class Model {
    /*Our Model class instance**/
    private static Model instance;
    /*This variable holds all the areas from the areas.json file**/
    private AreaList listOfAreas;
    /*This variable holds all items in the game from the items.json file**/
    private ItemList itemList;
    /*This variable holds the map object from the map.json file**/
    private GameMap map;

    /*Get an instance of the model
    * This makes it so we only ever have one instance of the model class
    * **/
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    /**Creation of model data starts here*/
    private Model() {
        try {
            setupModelData();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*Setup the data that needs to go inside the model**/
    private void setupModelData() throws IOException {
        Encoder en = new Encoder();
        map = en.decodeFromFile(GameMap.class,"map.json");
        listOfAreas = en.decodeFromFile(AreaList.class,"areas.json");
        itemList = en.decodeFromFile(ItemList.class,"items.json");
        itemList.setupItemList();

    }
    //TODO would it be a good idea just to make these 3 private? Would have to change test code however
    public AreaList getListOfAreas() {
        return listOfAreas;
    }


    public ItemList getItemList() {
        return itemList;
    }

    public GameMap getMap() {
        return map;
    }


    /*Returns a item that is tied to a given ID*
    * @param itemID the ID of the item we want
    * @return The required Item object, or null if nothing is found
    * */
    Item getItem(String itemID){
        return this.getItemList().getItem(itemID);
    }

    /*
    * Returns the ID of an item that was found by it's trigger
    * @param triggerStr The supposed trigger
    * @return String the id of the item whose trigger was passed in
    * **/
    String stringIsTrigger(String triggerStr){
        return this.getItemList().findIDByTrigger(triggerStr);
    }
}
