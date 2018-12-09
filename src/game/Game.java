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
        World world = new World(new Model("w1-map.json", "w1-areas.json", "w1-items.json", "enemies.json"), new Player());
        setup(world);
        run(world);
    }

    private static void run(World world) {
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

    private static void setup(World world) {
        world.printStartingWorldDialog();
        world.getPlayerName();
        world.allocateStartingStats();
        System.out.println("You are now ready to start the game! Type 'help' at anytime for a list of available commands.\n");
    }
}
