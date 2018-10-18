package Game.encode;


import Game.model.Item;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;


import java.util.Arrays;

//import static org.junit.Assert.*;

//@RunWith(Arquillian.class)
public class EncoderTest {
    /*@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Encoder.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }*/
    private static Encoder en;

    @BeforeAll
     static void setup(){
        en = new Encoder();
    }
    @Test
    void encode() throws Exception {
        Item item = new Item("1","stuff",true, Arrays.asList("it","buy","do"));
        en.encode(item,"items.json");
        Item item2 = new Item("12","stuff2",true, Arrays.asList("it","buy","do"));
        en.encode(item2,"items.json");

    }

    @Test
    void decode() throws Exception {
        Item item = (Item)en.decode(Item.class,"items.json");
        Assertions.assertNotNull(item != null);
    }
}
