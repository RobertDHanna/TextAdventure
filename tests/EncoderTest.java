import game.encode.Encoder;
import game.model.Item;
import org.junit.jupiter.api.*;
import java.util.Arrays;

class EncoderTest {

    private static Encoder en;

    @BeforeAll
     static void setup(){
        en = new Encoder();
    }

    @Test
    void encode() {
        Item item = new Item("1", "Stuff","stuff",true,"I'm in a room", Arrays.asList("it","buy","do"));
        String str = en.encode(item);
    }

    @Test
    void decode() {
        Item item = en.decode(Item.class,"{\"id\":\"1\",\"description\":\"stuff\",\"pickupable\":true,\"actionTriggers\":[\"it\",\"buy\",\"do\"]}");
        Assertions.assertNotNull(item);
    }
}
