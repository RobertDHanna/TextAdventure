import game.model.IModel;
import game.model.Model;
import game.model.Player;
import game.model.World;
import org.junit.jupiter.api.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    private static IModel model;
    private static World world;
    //The old stdout
    private PrintStream oldStream;
    //our new stdout, the Interceptor class
    private Interceptor newStream;

    /**
     * This class holds the list of responses coming in from the main program
     * it intercepts them and allows us to look at them to determine correctness
     * This class could be brought out of ActionTest if wanted. But for now it is an Inner class
     * */
    private class Interceptor extends PrintStream
    {
        private ArrayList<String> response;
        public Interceptor(OutputStream out)
        {
            super(out, true);
            response = new ArrayList<>();
        }
        @Override
        public void print(String s)
        {
            //super.print(s);
            //add incoming responses to a arraylist
            response.add(s);
        }

        public ArrayList<String> getResponse(){
            return response;
        }
    }

    //Before each class create a new Interceptor object to track responses
    @BeforeEach
    void setup(){
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
                    "                   \"traversableName\": \"Blue Door\"," +
                    "                   \"description\": \"An old blue door\"," +
                    "                   \"traversableActionString\": \"You open the door and go through.\"," +
                    "                   \"inRoomDescription\": \"You can see a Blue Door.\"," +
                    "                   \"requiredItemIds\": []" +
                    "               }" +
                    "           ]" +
                    "       }," +
                    "       {" +
                    "           \"areaId\": \"lonely_hideout\"," +
                    "           \"traversables\": [" +
                    "               {" +
                    "                   \"toAreaId\": \"candle_room\"," +
                    "                   \"traversableName\": \"Green Door\"," +
                    "                   \"description\": \"A shiny green door\"," +
                    "                   \"traversableActionString\": \"The handle of the door is ice cold. You pull it open and go through.\"," +
                    "                   \"inRoomDescription\": \"You see a Green Door leading to a room with a candle.\"," +
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
                    "           \"itemIds\": [ \"bed\" ]" +
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
                    "       }, {" +
                    "           \"id\": \"bed\"," +
                    "           \"description\": \"a bed\"," +
                    "           \"pickupable\": true," +
                    "           \"inRoomDescription\": \"There's a bed here.\"," +
                    "           \"actionTriggers\": [" +
                    "               \"bed\"" +
                    "           ]" +
                    "       }" +
                    "   ]" +
                    "}");
            world = new World(model, new Player());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // Save the old StdOut and let the interceptor be the new one
        oldStream = System.out;
        newStream = new Interceptor((oldStream));
        System.setOut(newStream);
    }
    //replace stdout
    @AfterEach
    void tearDown(){
        System.setOut(oldStream);
    }

    @Test
    void testGoUnConnectedRoom(){
        String input = "go Green Door";
        String lastDescription = world.getCurrentPlayerAreaDescription();
        world.handleInput(input);
        assertEquals(world.getCurrentPlayerAreaDescription(), lastDescription);
    }
    //Go to the next room
    @Test
    void testGo(){
        String input = "go Blue Door";
        String lastDescription = world.getCurrentPlayerAreaDescription();
        world.handleInput(input);
        assertNotEquals(world.getCurrentPlayerAreaDescription(), lastDescription);
        assertTrue(newStream.getResponse().contains("The room smells like popcorn, but you only see a table without a microwave.. How concerning."));
    }


    @Test
    void testGoNonExistingRoom(){
        String input = "go Fake Door";
        String lastDescription = world.getCurrentPlayerAreaDescription();
        world.handleInput(input);
        assertTrue(newStream.getResponse().contains("I'm not sure where that is...\n"));
        assertEquals(world.getCurrentPlayerAreaDescription(), lastDescription);
    }

    @Test
    void testGoItemID(){
        String input = "go candle";
        String lastDescription = world.getCurrentPlayerAreaDescription();
        world.handleInput(input);
        assertEquals(world.getCurrentPlayerAreaDescription(), lastDescription);
    }
    //Go back to candle room
    @Test
    void testGoBack(){
        String lastDescription = world.getCurrentPlayerAreaDescription();
        world.handleInput("go Blue Door");
        assertNotEquals(world.getCurrentPlayerAreaDescription(), lastDescription);
        assertTrue(newStream.getResponse().contains("The Lonely Hideout"));
        assertTrue(newStream.getResponse().contains("The room smells like popcorn, but you only see a table without a microwave.. How concerning."));

        System.setOut(newStream = new Interceptor(oldStream));
        lastDescription = world.getCurrentPlayerAreaDescription();
        world.handleInput("go Green Door");
        assertNotEquals(world.getCurrentPlayerAreaDescription(), lastDescription);
        assertTrue(newStream.getResponse().contains("The Candle Room"));
        assertTrue(newStream.getResponse().contains("The room is very plain and mostly empty."));
        assertTrue(newStream.getResponse().contains("There is a candle stick lying on the ground. It seems to have a slight glow although it is not lit."));
    }

    @Test
    void testInspectItem(){
        world.handleInput("inspect candle");
        assertTrue(newStream.getResponse().contains("just an ordinary candle stick"));
    }

    @Test
    void testInspectNonExistItem(){
        world.handleInput("inspect mop");
        assertTrue(newStream.getResponse().contains("No such item 'mop' in this area\n"));
    }

    @Test
    void testInspectItemNotInRoom(){
        world.handleInput("inspect bed");
        assertTrue(newStream.getResponse().contains("No such item 'bed' in this area\n"));
    }
}