package game;

import game.model.Model;
import game.model.Player;
import game.model.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by roberthanna on 10/15/18.
 */
public class Game
{
    public static void main(String[] args) {
        World world = new World(new Model("w1-map.json", "w1-areas.json", "w1-items.json"), new Player());
        run(world);
    }

    private static void run(World world) {
        world.printStartingWorldDialog();
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = "";
            World.print(">>: ");
            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            world.handleInput(input);
        }
    }
}
