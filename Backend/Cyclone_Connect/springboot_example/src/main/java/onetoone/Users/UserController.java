package onetoone.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;




@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userServices.getUserById(userId);
        if (user != null) {

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Fetch all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userServices.getAllUsers();
    }


    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        if (!userServices.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userServices.deleteUserById(userId);
        String message = "User with ID " + userId + " has been deleted.";
        return ResponseEntity.ok(message); // Pass the message here
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        if (!userServices.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        User updatedUser = userServices.updateUser(userId, userDetails);
        return ResponseEntity.ok(new Object() {
            public final String message = "User with ID " + userId + " has been updated.";
            public final User user = updatedUser;
        });
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User loginRequest) {
        User user = userServices.getUserByEmail(loginRequest.getEmail());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("userId", user.getUserId()); // Use getUserId() instead of getId()
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }
    }



    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User newUser) {
        // Check if the email already exists
        User existingUser = userServices.getUserByEmail(newUser.getEmail());
        if (existingUser != null) {
            // If user exists, return a response indicating the email is already in use
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // If the email doesn't exist, proceed with saving the new user
        User savedUser = userServices.saveUser(newUser); // This method should return the saved User object

        // Check if the user was successfully saved
        if (savedUser != null) {
            // Return a response entity indicating successful registration
            return ResponseEntity.ok("User registered successfully");
        }
        else {

            // Handle the case where user could not be saved due to some issue
            return ResponseEntity.badRequest().body("Error: User could not be registered");
        }
    }

}
