package onetoone.NavigateMap;

import onetoone.NavigateMap.Maps;
import onetoone.NavigateMap.MapsRepository;
import onetoone.NavigateMap.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapsServiceImpl implements MapsService {

    @Autowired
    private MapsRepository mapsRepository;

    @Override
    public Maps createMap(Maps map) {
        return mapsRepository.save(map);
    }


    @Override
    public List<Maps> getAllMaps() {
        return mapsRepository.findAll();
    }

    @Override
    public Maps getMapById(Long id) {
        Optional<Maps> map = mapsRepository.findById(id);
        return map.orElse(null);
    }

    @Override
    public Maps updateMap(Long id, Maps mapDetails) {
        Maps map = getMapById(id);
        if (map != null) {
            map.setDestination(mapDetails.getDestination());
            // Set other fields from mapDetails
            return mapsRepository.save(map);
        }
        return null; // Or handle accordingly
    }

    @Override
    public void deleteMap(Long id) {
        mapsRepository.deleteById(id);
    }
}
