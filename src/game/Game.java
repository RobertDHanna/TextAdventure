package game;

import game.model.Model;
import game.model.World;

/**
 * Created by roberthanna on 10/15/18.
 */
public class Game
{
    public static void main(String[] args) {
        World world = new World(Model.getInstance());
    }

    public void run() {
    }
}
