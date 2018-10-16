package Game;

import Game.encode.Encoder;

public class Main {

    public static void main(String[] args) throws Exception {
        Encoder en = new Encoder();
        en.decode(Game.class, "test.JSON");
    }

}
