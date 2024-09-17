package onetoone.Navigate;

import java.util.List;
import java.util.Optional;

import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/navigations")
public class NavigateController {

    private final NavigateRepository navigateRepository;

    private final UserRepository userRepository;

    @Autowired
    public NavigateController(NavigateRepository navigateRepository, UserRepository userRepository) {
        this.navigateRepository = navigateRepository;
        this.userRepository = userRepository;
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createNavigate(@RequestBody NavigateDTO navigateDTO) {
        return userRepository.findById(navigateDTO.getUserId())
                .map(user -> {
                    Navigate navigate = new Navigate(navigateDTO.getDestination(), navigateDTO.getDescription(), user);
                    navigate = navigateRepository.save(navigate);

                    Map<String, Object> successResponse = new HashMap<>();
                    successResponse.put("message", "Navigation created successfully");
                    successResponse.put("id", navigate.getId());

                    return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
                })
                .orElseGet(() -> {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("error", "User not found with ID: " + navigateDTO.getUserId());

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                });
    }


//    @PostMapping
//    public ResponseEntity<?> createNavigate(@RequestBody NavigateDTO navigateDTO) {
//        return userRepository.findById(navigateDTO.getUserId())
//                .map(user -> {
//                    Navigate navigate = new Navigate(navigateDTO.getDestination(), navigateDTO.getDescription(), user);
//                    navigate = navigateRepository.save(navigate);
//                    return ResponseEntity.status(HttpStatus.CREATED).body("Navigation created successfully with ID: " + navigate.getId());
//                })
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + navigateDTO.getUserId()));
//    }

    @GetMapping
    public ResponseEntity<?> getAllNavigates() {
        List<Navigate> navigations = navigateRepository.findAll();
        if (navigations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(navigations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNavigateById(@PathVariable Long id) {
        Optional<Navigate> navigation = navigateRepository.findById(id);
        return navigation
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Navigation not found with ID: " + id));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateNavigate(@PathVariable Long id, @RequestBody NavigateDTO navigateDTO) {
        return navigateRepository.findById(id).map(existingNavigate -> {
            // This line is removed: userRepository.findById(navigateDTO.getUserId()).ifPresent(existingNavigate::setUser);
            existingNavigate.setDestination(navigateDTO.getDestination());
            existingNavigate.setDescription(navigateDTO.getDescription());
            Navigate updatedNavigate = navigateRepository.save(existingNavigate);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Navigation updated successfully");
            response.put("id", updatedNavigate.getId());

            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Navigation not found with ID: " + id);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        });
    }






//    @PutMapping("/{id}")
//    public ResponseEntity<Map<String, Object>> updateNavigate(@PathVariable Long id, @RequestBody NavigateDTO navigateDTO) {
//        return navigateRepository.findById(id).map(existingNavigate -> {
//            userRepository.findById(navigateDTO.getUserId()).ifPresent(existingNavigate::setUser);
//            existingNavigate.setDestination(navigateDTO.getDestination());
//            existingNavigate.setDescription(navigateDTO.getDescription());
//            Navigate updatedNavigate = navigateRepository.save(existingNavigate);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "Navigation updated successfully");
//            response.put("id", updatedNavigate.getId());
//
//            return ResponseEntity.ok(response); // This returns ResponseEntity<Map<String, Object>>
//        }).orElseGet(() -> {
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Navigation not found with ID: " + id);
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse); // This also returns ResponseEntity<Map<String, Object>>
//        });
//    }



//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateNavigate(@PathVariable Long id, @RequestBody NavigateDTO navigateDTO) {
//        return navigateRepository.findById(id).map(existingNavigate -> {
//            userRepository.findById(navigateDTO.getUserId()).ifPresent(existingNavigate::setUser);
//            existingNavigate.setDestination(navigateDTO.getDestination());
//            existingNavigate.setDescription(navigateDTO.getDescription());
//            Navigate updatedNavigate = navigateRepository.save(existingNavigate);
//            return ResponseEntity.ok("Navigation updated successfully with ID: " + updatedNavigate.getId());
//        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Navigation not found with ID: " + id));
//    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNavigate(@PathVariable Long id) {
        Optional<Navigate> navigationOptional = navigateRepository.findById(id);
        if (navigationOptional.isPresent()) {
            navigateRepository.deleteById(id);
            return ResponseEntity.ok("Navigation deleted successfully with ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Navigation not found with ID: " + id);
        }
    }
}
