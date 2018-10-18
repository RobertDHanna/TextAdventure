package Game.tests;


import Game.encode.Encoder;
import Game.model.Item;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;


import java.util.Arrays;

//import static org.junit.Assert.*;

class EncoderTest {

    private static Encoder en;

    @BeforeAll
     static void setup(){
        en = new Encoder();
    }
    @Test
    void encode() throws Exception {
        Item item = new Item("1","stuff",true, Arrays.asList("it","buy","do"));
        en.encode(item,"itemsTest.json");;

    }

    @Test
    void decode() throws Exception {
        Item item = (Item)en.decode(Item.class,"itemsTest.json");
        Assertions.assertNotNull(item != null);
    }
}
