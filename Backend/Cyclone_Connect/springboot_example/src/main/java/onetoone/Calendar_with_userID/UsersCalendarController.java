package onetoone.Calendar_with_userID;

import onetoone.Users.User;
import onetoone.Users.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/calendar")
public class UsersCalendarController {

    private final UserServices userServices;
    private final UserCalendarRepository usercalendarRepository;

    private final UserCalendarService userCalendarService;



    private CalendarEventDTO convertToDTO(CalendarEntity entity) {
        CalendarEventDTO dto = new CalendarEventDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setDate(entity.getDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        // Add other fields as needed
        return dto;
    }
    private CalendarEventWithUserDTO convertToCalendarEventWithUserDTO(CalendarEntity entity) {
        CalendarEventWithUserDTO dto = new CalendarEventWithUserDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setDate(entity.getDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        User user = entity.getUser();
        if (user != null) {
            dto.setUserId(user.getUserId());
            dto.setUsername(user.getFirstName() + " " + user.getLastName());
            // Set other user-related fields as needed
        }
        return dto;
    }

    @Autowired
    public UsersCalendarController(UserServices userServices, UserCalendarRepository usercalendarRepository, UserCalendarService userCalendarService) {
        this.userServices = userServices;
        this.usercalendarRepository = usercalendarRepository;
        this.userCalendarService = userCalendarService;

    }

    @GetMapping("/generateDummySchedules/{userId}")
    public ResponseEntity<?> generateAndRetrieveDummySchedules(@PathVariable Long userId) {
        try {
            User user = userServices.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            List<CalendarEntity> schedules = userCalendarService.generateDummySchedulesForUser(user);
            return ResponseEntity.ok(schedules);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while generating schedules");
        }
    }

    private User retrieveUserById(Long userId) {
        // This method needs to be implemented to retrieve a User entity by its ID
        // Return the User object after fetching it from the database
        return new User(); // Placeholder for actual user retrieval logic
    }

    @GetMapping("/events-with-users")
    public ResponseEntity<List<CalendarEventWithUserDTO>> getAllEventsWithUsers() {
        List<CalendarEntity> events = usercalendarRepository.findAllWithUsers();
        List<CalendarEventWithUserDTO> dtos = events.stream()
                .map(this::convertToCalendarEventWithUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<CalendarEventDTO>> getAllEvents() {
        List<CalendarEntity> events = usercalendarRepository.findAll();
        List<CalendarEventDTO> dtos = events.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/user/{userId}/events")
    public ResponseEntity<List<CalendarEventDTO>> getUserEvents(@PathVariable Long userId) {
        User user = userServices.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CalendarEntity> events = usercalendarRepository.findByUserId(userId);
        List<CalendarEventDTO> dtos = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CalendarEventDTO> getEventById(@PathVariable("id") Long id) {
        return usercalendarRepository.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @PostMapping("/user/{userId}/events")
    public ResponseEntity<Map<String, Object>> createEvent(@PathVariable Long userId, @RequestBody CalendarEventDTO eventDTO) {
        // First, find the user by ID to ensure they exist

        User user = userServices.getUserById(userId);
        if (user == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        // Create a new CalendarEntity and set its user
        CalendarEntity event = convertToEntity(eventDTO);
        event.setUser(user);

        // Save the new event
        CalendarEntity savedEvent = usercalendarRepository.save(event);

        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("message", "Event created successfully");
        successResponse.put("eventId", savedEvent.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    private CalendarEntity convertToEntity(CalendarEventDTO dto) {
        CalendarEntity entity = new CalendarEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        // Add other fields as necessary
        return entity;
    }





    @PutMapping("/{userId}/{eventId}")
    public ResponseEntity<Map<String, Object>> updateEvent(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId,
            @RequestBody CalendarEntity updatedEvent
    )  {
        return usercalendarRepository.findByIdAndUser_UserId(eventId, userId).map(event -> {
            // Explicitly update the desired fields from updatedEvent to event
            event.setTitle(updatedEvent.getTitle());
            event.setDescription(updatedEvent.getDescription());
            event.setDate(updatedEvent.getDate());
            event.setStartTime(updatedEvent.getStartTime());
            event.setEndTime(updatedEvent.getEndTime());
            // Assume other fields are set similarly
            // The user of the event is not changed

            CalendarEntity savedEvent = usercalendarRepository.save(event);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Event updated successfully");
            response.put("eventId", savedEvent.getId());
            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Event not found with ID: " + eventId + " for User ID: " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }




    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<Map<String, Object>> deleteEvent(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId
    ) {
        return usercalendarRepository.findByIdAndUser_UserId(eventId, userId).map(event -> {
            usercalendarRepository.deleteByIdAndUser_UserId(eventId, userId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Event deleted successfully.");
            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Event not found with ID: " + eventId + " for User ID: " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }







}
