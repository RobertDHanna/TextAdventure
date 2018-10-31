package game.model;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    private static Model model;
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

    @BeforeAll
    static void setUp() {
        try {
            model = Model.getInstance();
            world = new World(model);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    //Before each class create a new Interceptor object to track responses
    @BeforeEach
    void setup(){
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
        String input = "go Metal Door";
        String lastDescription = world.getCurrentPlayerAreaDescription();
        world.handleInput(input);
        assertEquals(world.getCurrentPlayerAreaDescription(), lastDescription);
    }
    //Go to the next room
    @Test
    void testGo(){
        String input = "go Wooden Door";
        String lastDescription = world.getCurrentPlayerAreaDescription();
        world.handleInput(input);
        assertNotEquals(world.getCurrentPlayerAreaDescription(), lastDescription);
        assertTrue(newStream.getResponse().contains("You see an empty bedroll and a dead campfire. You see a hole in the ground in the corner of the room."));
    }


    @Test
    void testGoNonExistingRoom(){
        String input = "go candy Door";
        String lastDescription = world.getCurrentPlayerAreaDescription();
        world.handleInput(input);
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
        world.handleInput("go Metal Door");
        assertNotEquals(world.getCurrentPlayerAreaDescription(), lastDescription);
        assertTrue(newStream.getResponse().contains("The Candle Room"));
        assertTrue(newStream.getResponse().contains("The room is empty except for a candle. You can see a doorway."));
    }

    @Test
    void testInspectItem(){
        world.handleInput("inspect Candle");
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