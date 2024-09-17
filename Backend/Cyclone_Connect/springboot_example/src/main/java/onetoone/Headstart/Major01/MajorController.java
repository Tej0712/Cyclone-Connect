package onetoone.Headstart.Major01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/majors")
public class MajorController {

    @Autowired
    private MajorRepository majorRepository;

    @GetMapping
    public ResponseEntity<List<Major>> getAllMajors() {
        List<Major> majors = majorRepository.findAll();
        return ResponseEntity.ok(majors); // This returns a JSON array of Major objects
    }

    @GetMapping("/{id}")
    public ResponseEntity<Major> getMajorById(@PathVariable Long id) {
        Optional<Major> majorOptional = majorRepository.findById(id);
        return majorOptional
                .map(ResponseEntity::ok) // This returns a single Major object as JSON
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMajor(@RequestBody Major major) {
        Major savedMajor = majorRepository.save(major);

        // Creating JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Major created successfully");
        response.put("major", savedMajor);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMajor(@PathVariable Long id, @RequestBody Major major) {
        if (!majorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        major.setMajorID(id); // Ensure the correct ID is set
        Major updatedMajor = majorRepository.save(major);

        // Creating JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Major updated successfully");
        response.put("major", updatedMajor);

        return ResponseEntity.ok(response);
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajor(@PathVariable Long id) {
        if (!majorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        majorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}