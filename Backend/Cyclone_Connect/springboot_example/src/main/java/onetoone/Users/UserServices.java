package onetoone.Users;

import jakarta.transaction.Transactional;
import onetoone.Calendar_with_userID.UserCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServices {
    private final UserRepository userRepository;
    private final UserCalendarService userCalendarService;



    @Autowired
    public UserServices(UserRepository userRepository, UserCalendarService userCalendarService) {
        this.userRepository = userRepository;
        this.userCalendarService = userCalendarService;
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User saveUser(User user) {
        // Save the user to the database and return the saved entity
        return userRepository.save(user);
    }


//    public void saveUser(User user) {
//        userRepository.save(user);
//    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    public User updateUser(Long userId, User userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setAccountType(userDetails.getAccountType());

        return userRepository.save(user);
    }
    @Transactional
    public User registerNewUser(String email, String password, String firstName, String lastName, String accountType) {
        // User creation logic here...
        // Create a new User object and populate it with parameter values
        User newUser = new User(/* parameters */);

        // Save the new user to the database
        newUser = userRepository.save(newUser);

        // Generate and save the class schedule for the new user
        userCalendarService.generateDummySchedulesForUser(newUser);

        // Return the saved User object
        return newUser;
    }





}