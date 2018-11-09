package game.model;

import game.encode.Encoder;

import java.io.IOException;

/*
*
* This class holds all the data for the game. When the game starts up the model is created
*
*
 */

public class Model implements IModel {
    /*This variable holds all the areas from the w1-areas.json file**/
    private AreaList listOfAreas;
    /*This variable holds all items in the game from the w1-items.json file**/
    private ItemList itemList;
    /*This variable holds the map object from the w1-map.json file**/
    private GameMap map;

    /**Creation of model data starts here*/
    public Model() {
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
        map = en.decodeFromFile(GameMap.class,"w1-map.json");
        listOfAreas = en.decodeFromFile(AreaList.class,"w1-areas.json");
        itemList = en.decodeFromFile(ItemList.class,"w1-items.json");
        itemList.setupItemList();

    }
    public AreaList getListOfAreas() {
        return listOfAreas;
    }


    public ItemList getItemList() {
        return itemList;
    }

    public GameMap getMap() {
        return map;
    }
}
