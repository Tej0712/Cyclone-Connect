package onetoone.NavigateMap;



import onetoone.NavigateMap.Maps;

import java.util.List;

public interface MapsService {

    Maps createMap(Maps map);

    List<Maps> getAllMaps();
    Maps getMapById(Long id);
    Maps updateMap(Long id, Maps mapDetails);
    void deleteMap(Long id);
}
