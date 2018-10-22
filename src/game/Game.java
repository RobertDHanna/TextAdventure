package game;

import game.model.Model;
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
        World world = new World(Model.getInstance());
        run(world);
    }

    private static void run(World world) {
        World.print(world.getStartingWorldDialog());
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
