package onetoone.NavigateMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maps")
public class MapsController {

    private final MapsService mapsService;


    @Autowired
    public MapsController(MapsService mapsService) {
        this.mapsService = mapsService;
    }

    @PostMapping
    public Maps createMap(@RequestBody Maps map) {
        return mapsService.createMap(map);
    }

    @GetMapping
    public List<Maps> getAllMaps() {
        return mapsService.getAllMaps();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maps> getMapById(@PathVariable Long id) {
        Maps map = mapsService.getMapById(id);
        if (map != null) {
            return ResponseEntity.ok(map);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maps> updateMap(@PathVariable Long id, @RequestBody Maps mapDetails) {
        Maps updatedMap = mapsService.updateMap(id, mapDetails);
        if (updatedMap != null) {
            return ResponseEntity.ok(updatedMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMap(@PathVariable Long id) {
        mapsService.deleteMap(id);
        return ResponseEntity.ok().build();
    }
}
