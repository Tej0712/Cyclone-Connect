package onetoone.EmergencyContacts;

import onetoone.EmergencyContacts.Emergency;
import onetoone.EmergencyContacts.EmergencyRepository; // Correct the import to match the package
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/emergencies")
public class EmergencyController {

    @Autowired
    private EmergencyRepository emergencyRepository;


    public EmergencyController(EmergencyRepository emergencyRepository) {
        this.emergencyRepository = emergencyRepository;
    }

    // Get all emergency contacts
    @GetMapping
    public List<Emergency> getAllEmergencies() {
        return emergencyRepository.findAll();
    }

    // Create a new emergency contact

    @PostMapping
    public ResponseEntity<Emergency> createEmergency(@RequestBody Emergency emergency) {
        Emergency savedEmergency = emergencyRepository.save(emergency);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmergency);
    }


//    @PostMapping
//    public Emergency createEmergency(@RequestBody Emergency emergency) {
//        return emergencyRepository.save(emergency);
//    }



    // Get a single emergency contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<Emergency> getEmergencyById(@PathVariable Long id) {
        Emergency emergency = emergencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emergency not found with id " + id));
        return ResponseEntity.ok(emergency);
    }



    // Update an emergency contact
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmergency(@PathVariable Long id, @RequestBody Emergency emergencyDetails) {
        Emergency emergency = emergencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emergency not found with id " + id));

        emergency.setContactName(emergencyDetails.getContactName());
        emergency.setPhoneNumber(emergencyDetails.getPhoneNumber());
        emergency.setDescription(emergencyDetails.getDescription());

        emergencyRepository.save(emergency);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Emergency contact with ID " + id + " was updated successfully.");
        response.put("emergencyId", id);

        return ResponseEntity.ok(response);
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateEmergency(@PathVariable Long id, @RequestBody Emergency emergencyDetails) {
//        Emergency emergency = emergencyRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Emergency not found with id " + id));
//
//        emergency.setContactName(emergencyDetails.getContactName());
//        emergency.setPhoneNumber(emergencyDetails.getPhoneNumber());
//        emergency.setDescription(emergencyDetails.getDescription());
//
//        emergencyRepository.save(emergency);
//
//        return ResponseEntity.ok("Emergency contact with ID " + id + " was updated successfully.");
//    }



    // Delete an emergency contact
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmergency(@PathVariable Long id) {
        return emergencyRepository.findById(id)
                .map(emergency -> {
                    emergencyRepository.delete(emergency);
                    return ResponseEntity.ok("Emergency contact with ID " + id + " was deleted successfully.");
                })
                .orElseThrow(() -> new RuntimeException("Emergency not found with id " + id));
    }


    // Get emergency contacts by contact name
    @GetMapping("/search")
    public ResponseEntity<List<Emergency>> getEmergencyByContactName(@RequestParam String name) {
        List<Emergency> emergencies = emergencyRepository.findByContactNameContainingIgnoreCase(name);
        if (emergencies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emergencies);
    }
}

