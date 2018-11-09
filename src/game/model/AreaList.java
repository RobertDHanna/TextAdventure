package game.model;

import java.util.List;
/*Holds the list of areas from the w1-areas.json file
*
* **/
public class AreaList {
    private List<Area> areas;

    public AreaList(List<Area> areas) {
        this.areas = areas;
    }

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
