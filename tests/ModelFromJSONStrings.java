import game.encode.Encoder;
import game.model.*;

public class ModelFromJSONStrings implements IModel {
    private final AreaList mAreaList;
    private final ItemList mItemList;
    private final GameMap mGameMap;
    private EnemyList mEnemyList;

    public ModelFromJSONStrings(String map, String areas, String items) {
        Encoder en = new Encoder();
        mGameMap = en.decode(GameMap.class, map);
        mAreaList = en.decode(AreaList.class, areas);
        mItemList = en.decode(ItemList.class, items);
    }

    @Override
    public AreaList getListOfAreas() {
        return mAreaList;
    }

    @Override
    public ItemList getItemList() {
        return mItemList;
    }

    @Override
    public GameMap getMap() {
        return mGameMap;
    }

    @Override
    public EnemyList getEnemyList() {
        return mEnemyList;
    }
}
