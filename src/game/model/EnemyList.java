package game.model;

import java.util.List;

public class EnemyList
{
    private List<Enemy> enemies;

    public EnemyList(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    /*Lets you get a specific enemy by its id
     * @param id The id to get the area by
     * @return Enemy object that corresponds to the id
     * **/
    public Enemy getEnemy(String id){
        for (Enemy enemy : enemies) {
            if (enemy.getId().equals(id)) {
                return enemy;
            }
        }
        return null;
    }

    public List<Enemy> getAreas() {
        return enemies;
    }
}
