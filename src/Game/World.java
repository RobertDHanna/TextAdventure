package Game;

import Game.Connection;
import Game.Player;

import java.util.HashMap;

public class World
{
    private HashMap<String, Connection> map = new HashMap<>();
    private Player player;
    private String currPlayerAreaId;

    public World(HashMap<String, Connection> map, Player player, String currPlayerAreaId)
    {
        this.map = map;
        this.player = player;
        this.currPlayerAreaId = currPlayerAreaId;
    }
    
    public void handleAction(String action) {
        // turn into List<String>
    }

    public void handlePlayerMove(String AreaId) {

    }
}
