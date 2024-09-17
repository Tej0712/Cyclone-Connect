package onetoone.Organizations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ObjectMapper objectMapper;  // Autowired ObjectMapper for manual JSON processing if needed


    // CREATE a new organization

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createOrganization(@RequestBody Organization organization) {
        try {
            Organization savedOrganization = organizationService.saveOrganization(organization);
            String json = objectMapper.writeValueAsString(savedOrganization);
            return new ResponseEntity<>(json, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Unable to create organization due to internal error");
            try {
                String errorJson = objectMapper.writeValueAsString(error);
                return new ResponseEntity<>(errorJson, HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception jsonException) {
                return new ResponseEntity<>("{\"error\":\"Internal server error\"}", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
//    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
//        Organization savedOrganization = organizationService.saveOrganization(organization);
//        return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
//    }


    // READ all organizations
    @GetMapping("/0")
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        List<Organization> organizations = organizationService.getAllOrganizations();
        return ResponseEntity.ok(organizations);
    }

    // READ one organization by ID
    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganization(@PathVariable Long organizationId) {
        Organization organization = organizationService.getOrganizationById(organizationId);
        return organization != null ? ResponseEntity.ok(organization) : ResponseEntity.notFound().build();
    }

    // GET all organizations a specific user is part of
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Organization>> getOrganizationsByUser(@PathVariable Long userId) {
        try {
            List<Organization> organizations = organizationService.getOrganizationsByUserId(userId);
            if (organizations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(organizations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // UPDATE an organization
    @PutMapping("/{organizationId}")
    public ResponseEntity<Organization> updateOrganization(
            @PathVariable Long organizationId, @RequestBody Organization organizationDetails) {
        Organization updatedOrganization = organizationService.updateOrganization(organizationId, organizationDetails);
        return updatedOrganization != null ? ResponseEntity.ok(updatedOrganization) : ResponseEntity.notFound().build();
    }

    // DELETE an organization
    @DeleteMapping("/{organizationId}")
    public ResponseEntity<?> deleteOrganization(@PathVariable Long organizationId) {
        if (organizationService.deleteOrganization(organizationId)) {
            return ResponseEntity.ok("Organization deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found.");
        }
    }

    // JOIN an organization

    // JOIN an organization
    @PostMapping("/{userId}/join/{organizationId}")
    public ResponseEntity<Map<String, String>> joinOrganization(@PathVariable Long userId, @PathVariable Long organizationId) {
        boolean success = organizationService.joinOrganization(userId, organizationId);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "User has joined the organization successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Unable to join the organization");
            return ResponseEntity.badRequest().body(response);
        }
    }
//    @PostMapping("/{userId}/join/{organizationId}")
//    public ResponseEntity<String> joinOrganization(
//            @PathVariable Long userId,
//            @PathVariable Long organizationId) {
//        boolean success = organizationService.joinOrganization(userId, organizationId);
//        return success ? ResponseEntity.ok("User has joined the organization successfully.") :
//                ResponseEntity.badRequest().body("Unable to join the organization.");
//    }

    // LEAVE an organization

    // LEAVE an organization
    @PostMapping("/{userId}/leave/{organizationId}")
    public ResponseEntity<Map<String, String>> leaveOrganization(@PathVariable Long userId, @PathVariable Long organizationId) {
        boolean success = organizationService.leaveOrganization(userId, organizationId);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "User has left the organization successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Unable to leave the organization");
            return ResponseEntity.badRequest().body(response);
        }
    }
//    @PostMapping("/{userId}/leave/{organizationId}")
//    public ResponseEntity<String> leaveOrganization(
//            @PathVariable Long userId,
//            @PathVariable Long organizationId) {
//        boolean success = organizationService.leaveOrganization(userId, organizationId);
//        return success ? ResponseEntity.ok("User has left the organization successfully.") :
//                ResponseEntity.badRequest().body("Unable to leave the organization.");
//    }
}
