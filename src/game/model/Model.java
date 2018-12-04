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

    private EnemyList listOfEnemies;

    /**Creation of model data starts here*/
    public Model(String mapName, String areasName, String itemsName, String enemiesName) {
        try {
            setupModelData(mapName, areasName, itemsName, enemiesName);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*Setup the data that needs to go inside the model**/
    private void setupModelData(String mapName, String areasName, String itemsName, String enemiesName) throws IOException {
        Encoder en = new Encoder();
        map = en.decodeFromFile(GameMap.class, mapName);
        listOfAreas = en.decodeFromFile(AreaList.class, areasName);
        itemList = en.decodeFromFile(ItemList.class,itemsName);
        itemList.setupItemList();
        listOfEnemies = en.decodeFromFile(EnemyList.class, enemiesName);

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
