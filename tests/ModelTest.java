import Game.model.AreaList;
import Game.model.ItemList;
import Game.model.Model;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private static Model model;
    @BeforeAll
    static void setUp() {
        try {
            model = Model.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    //Assert the map was created correctly
    @Test
    void notNullTest(){

        assertNotNull(model.getMap());
    }

    //Test to see if the areas are in the Map
    @Test
    void checkAreaIDsTest(){
        assertEquals(model.getMap().getStartAreaId(),"candle_room");
        assertEquals(model.getMap().getEndAreaId(),"dusty_cave");

        assertEquals(model.getMap().getConnectionList().get(0).getAreaId(),"candle_room");
        assertEquals(model.getMap().getConnectionList().get(1).getAreaId(),"lonely_hideout");
    }

    //Test to see if the correct connections are in the Map object
    @Test
    void checkConnectionsTest(){
        assertEquals(model.getMap().getConnectionList().get(0).getTraversables().size(),1);
        assertEquals(model.getMap().getConnectionList().get(1).getTraversables().size(),2);
    }

    //Test to see if the areas are in the AreaList object
    @Test
    void AreaListIDTest(){
        AreaList areas = model.getListOfAreas();
        assertNotNull(areas.getArea("candle_room"));
        assertNotNull(areas.getArea("lonely_hideout"));
        assertNotNull(areas.getArea("dusty_cave"));


    }

    //Test to see if the AreaList object has the right items
    @Test
    void AreaListItemTest(){

        AreaList areas = model.getListOfAreas();
        assertEquals(areas.getArea("dusty_cave").getItemIds().size(), 0);
        assertEquals(areas.getArea("lonely_hideout").getItemIds().size(), 2);
        assertEquals(areas.getArea("candle_room").getItemIds().size(), 1);

        assertEquals(areas.getArea("lonely_hideout").getItemIds().get(1), "hidden_key");
    }

    //Test to see if the Items are in the Item object by looking for their ids
    @Test
    void ItemListIDTest(){
        ItemList itemList = model.getItemList();
        assertNotNull(itemList.getItem("candle_stick"));
        assertNotNull(itemList.getItem("bedroll"));
        assertNotNull(itemList.getItem("hidden_key"));
    }
}