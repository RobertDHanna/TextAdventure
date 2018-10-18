package Game.model;

import java.util.ArrayList;
import java.util.List;
/*Holds the list of areas fro mthe areas.json file
*
* **/
public class AreaList {
    private List<Area> areas;

    public AreaList(List<Area> areas) {
        this.areas = areas;
    }

    /*public List<Area> getAreas() {
        return areas;
    }*/
    /*Lets you get a specific area by its id
    * @param id The id to get the area by
    * @return Area object that corresponds to the id
    * **/
    public Area getArea(String id){
        for (Area area : areas) {
            if (area.getId().equals(id)) {
                return area;
            }
        }
        return null;
    }
}
