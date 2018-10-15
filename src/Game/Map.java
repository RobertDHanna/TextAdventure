package Game;

import java.util.List;

public class Map
{
    private String startAreaId;
    private String endAreaId;
    private List<Connection> connectionList;

    public Map(String startAreaId, String endAreaId, List<Connection> connectionList)
    {
        this.startAreaId = startAreaId;
        this.endAreaId = endAreaId;
        this.connectionList = connectionList;
    }

    public String getStartAreaId()
    {
        return startAreaId;
    }

    public String getEndAreaId()
    {
        return endAreaId;
    }

    public List<Connection> getConnectionList()
    {
        return connectionList;
    }
}
