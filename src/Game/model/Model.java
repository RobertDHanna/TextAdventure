package Game.model;

import Game.encode.Encoder;

import java.util.ArrayList;
import java.util.List;

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
    private Map map;

    /*Get an instance of the model
    * This makes it so we only ever have one instance of the model class
    * **/
    public static Model getInstance() {
        if (instance == null) {
            try {
                instance = new Model();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return instance;
    }

    /**Creation of model data starts here*/
    private Model() throws Exception {
        setupModelData();
    }

    /*Setup the data that needs to go inside the model**/
    private void setupModelData() throws Exception {
        Encoder en = new Encoder();
        map = (Map) en.decode(Map.class,"map.json");
        listOfAreas = (AreaList) en.decode(AreaList.class,"areas.json");
        itemList = (ItemList) en.decode(ItemList.class,"items.json");

    }

    public AreaList getListOfAreas() {
        return listOfAreas;
    }


    public ItemList getItemList() {
        return itemList;
    }

    public Map getMap() {
        return map;
    }
}
