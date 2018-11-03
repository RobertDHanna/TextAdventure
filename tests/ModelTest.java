import game.model.AreaList;
import game.model.IModel;
import game.model.ItemList;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private static IModel model;

    @BeforeAll
    static void setUp() {
        try {
            model = new ModelFromJSONStrings("{" +
                    "   \"startAreaId\": \"candle_room\"," +
                    "   \"endAreaId\": \"lonely_hideout\"," +
                    "   \"connectionList\": [" +
                    "       {" +
                    "           \"areaId\": \"candle_room\"," +
                    "           \"traversables\": [" +
                    "               {" +
                    "                   \"toAreaId\": \"lonely_hideout\"," +
                    "                   \"traversableName\": \"Wooden Door\"," +
                    "                   \"description\": \"An old wooden door\"," +
                    "                   \"traversableActionString\": \"You open the door and go through.\"," +
                    "                   \"inRoomDescription\": \"You can see a Wooden Door.\"," +
                    "                   \"requiredItemIds\": []" +
                    "               }" +
                    "           ]" +
                    "       }," +
                    "       {" +
                    "           \"areaId\": \"lonely_hideout\"," +
                    "           \"traversables\": [" +
                    "               {" +
                    "                   \"toAreaId\": \"candle_room\"," +
                    "                   \"traversableName\": \"Wooden Door\"," +
                    "                   \"description\": \"A shiny metal door\"," +
                    "                   \"traversableActionString\": \"The handle of the door is ice cold. You pull it open and go through.\"," +
                    "                   \"inRoomDescription\": \"You see a Wooden Door leading to a room with a candle.\"," +
                    "                   \"requiredItemIds\": []" +
                    "               }" +
                    "           ]" +
                    "       }" +
                    "   ]" +
                    "}", "{" +
                    "   \"areas\": [" +
                    "       {" +
                    "           \"id\": \"candle_room\"," +
                    "           \"name\": \"The Candle Room\"," +
                    "           \"description\": \"The room is very plain and mostly empty.\"," +
                    "           \"itemIds\": [" +
                    "               \"candle_stick\"" +
                    "           ]" +
                    "       }," +
                    "       {" +
                    "           \"id\": \"lonely_hideout\"," +
                    "           \"name\": \"The Lonely Hideout\"," +
                    "           \"description\": \"The room smells like popcorn, but you only see a table without a microwave.. How concerning.\"," +
                    "           \"itemIds\": [ ]" +
                    "       }" +
                    "   ]" +
                    "}", "{" +
                    "   \"items\": [" +
                    "       {" +
                    "           \"id\": \"candle_stick\"," +
                    "           \"description\": \"just an ordinary candle stick\"," +
                    "           \"pickupable\": true," +
                    "           \"inRoomDescription\": \"There is a candle stick lying on the ground. It seems to have a slight glow although it is not lit.\"," +
                    "           \"actionTriggers\": [" +
                    "               \"candle\"" +
                    "           ]" +
                    "       }" +
                    "   ]" +
                    "}");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    //Assert the map was created correctly
    @Test
    void notNullTest() {

        assertNotNull(model.getMap());
    }

    //Test to see if the areas are in the GameMap
    @Test
    void checkAreaIDsTest() {
        assertEquals(model.getMap().getStartAreaId(), "candle_room");
        assertEquals(model.getMap().getEndAreaId(), "lonely_hideout");

        assertEquals(model.getMap().getConnectionList().get(0).getAreaId(), "candle_room");
        assertEquals(model.getMap().getConnectionList().get(1).getAreaId(), "lonely_hideout");
    }

    //Test to see if the correct connections are in the GameMap object
    @Test
    void checkConnectionsTest() {
        assertEquals(model.getMap().getConnectionList().get(0).getTraversables().size(), 1);
        assertEquals(model.getMap().getConnectionList().get(1).getTraversables().size(), 1);
    }

    //Test to see if the areas are in the AreaList object
    @Test
    void areaListIDTest() {
        AreaList areas = model.getListOfAreas();
        assertNotNull(areas.getArea("candle_room"));
        assertNotNull(areas.getArea("lonely_hideout"));
    }

    //Test to see if the AreaList object has the right items
    @Test
    void areaListItemTest() {

        AreaList areas = model.getListOfAreas();
        assertEquals(areas.getArea("candle_room").getItemIds().size(), 1);
        assertEquals(areas.getArea("lonely_hideout").getItemIds().size(), 0);

        assertEquals(areas.getArea("candle_room").getItemIds().get(0), "candle_stick");
    }

    //Test to see if the Items are in the Item object by looking for their ids
    @Test
    void itemListIDTest() {
        ItemList itemList = model.getItemList();
        assertNotNull(itemList.getItem("candle_stick"));
    }
}