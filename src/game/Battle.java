package game;

import game.model.Enemy;
import game.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Battle {

    //May make use of these in the future?
    private int turnCount;
    private int turnLimit;

    public Battle() {

    }

    public void run(Player player, Enemy enemy) {

        System.out.println("**************************************************************\n");
        System.out.println("You start the fight with the goblin!\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        while (player.getCurrentHp() > 0 && enemy.getCurrentHp() > 0) {

            printBattleInfo(player, enemy);
            System.out.println("What would you like to do?\n");
            System.out.println("1: Attack");
            System.out.println("2: Run\n");
            System.out.print(">>: ");

            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

            System.out.print("\n");
            input = input.trim().toLowerCase();

            switch(input) {
                case "1":
                case "attack":
                    //If the player got a successful hit
                    if(calculateHit(player.getDexterity(), enemy.getDexterity())) {
                        int playerDamage = player.getHitDamage();
                        enemy.takeDamage(playerDamage);
                        System.out.println(String.format("You hit the %s for %d damage!", enemy.getName(), playerDamage));
                    } else {
                        System.out.println(String.format("You swing at the %s and miss!", enemy.getName()));
                    }
                    //If the enemy got a successful hit
                    if(calculateHit(enemy.getDexterity(), player.getDexterity())) {
                        int enemyDamage = enemy.getHitDamage();
                        player.takeDamage(enemyDamage);
                        System.out.println(String.format("The %s hits you for %d damage!", enemy.getName(), enemyDamage));
                    } else {
                        System.out.println(String.format("The %s lunges at you but misses.", enemy.getName()));
                    }
                    System.out.print("\n");
                    break;
                case "2":
                case "run":
                    System.out.println("You can't run from this fight!\n");
                    break;
                default:
                    System.out.println("I don't recognize that command.\n");
            }
        }

        if(enemy.getCurrentHp() <= 0) {
            System.out.println(String.format("You defeated the %s!\n", enemy.getName()));
        }
        if(player.getCurrentHp() <= 0) {
            System.out.println("Oh dear, you are dead!\n");
        }
    }

    private boolean calculateHit(int attacker_dex, int defender_dex) {

        double hitChance = ((double)attacker_dex/(double)defender_dex) * 50;

        Random rand = new Random();
        int r = rand.nextInt(100) + 1;

        if (r < hitChance) {
            return true;
        } else {
            return false;
        }
    }

    private void printBattleInfo(Player player, Enemy enemy) {

        System.out.print(String.format("Enemy Health: %s/%s\n\n", enemy.getCurrentHp(), enemy.getMaxHp()));
        System.out.println(String.format(" Your Health: %s/%s", player.getCurrentHp(), player.getMaxHp()));
        System.out.println(String.format("     Your AP: %s/%s\n", player.getCurrentAp(), player.getMaxAp()));
    }

}
