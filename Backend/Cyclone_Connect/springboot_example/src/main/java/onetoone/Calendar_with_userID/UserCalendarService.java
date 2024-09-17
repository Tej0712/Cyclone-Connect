package onetoone.Calendar_with_userID;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import onetoone.Users.User;

@Service
public class UserCalendarService {

    private final UserCalendarRepository userCalendarRepository;

    // Dummy course titles, replace with actual course data as needed
    private static final String[] COURSES = {"Mathematics", "Physics", "Chemistry", "History", "English"};

    // Constructor for autowiring the repository
    public UserCalendarService(UserCalendarRepository userCalendarRepository) {
        this.userCalendarRepository = userCalendarRepository;
    }

    // Method to generate and persist a list of dummy class schedules for a given user for the fall semester
    @Transactional
    public List<CalendarEntity> generateDummySchedulesForUser(User user) {
        // Check if the user already has a schedule for the fall semester of 2024
        LocalDate semesterStart = LocalDate.of(2024, 9, 1);
        LocalDate semesterEnd = LocalDate.of(2024, 12, 15);

        // Check if the user already has a schedule
        if (userCalendarRepository.existsByUser_UserIdAndDateBetween(user.getUserId(), semesterStart, semesterEnd)) {
            throw new IllegalStateException("Schedule already generated for user with ID: " + user.getUserId());
        }

        Random random = new Random();
        List<CalendarEntity> schedules = new ArrayList<>();

        for (LocalDate date = semesterStart; !date.isAfter(semesterEnd); date = date.plusDays(1)) {
            if (date.getDayOfWeek().getValue() >= 1 && date.getDayOfWeek().getValue() <= 5) {
                CalendarEntity schedule = new CalendarEntity();
                schedule.setUser(user);
                schedule.setDate(date);
                schedule.setTitle(COURSES[random.nextInt(COURSES.length)]);

                int startHour = 8 + random.nextInt(7); // Classes start between 8 AM and 3 PM
                schedule.setStartTime(LocalTime.of(startHour, 0));
                schedule.setEndTime(LocalTime.of(startHour + 1, 0)); // 1-hour duration

                // Persist the schedule entity to the database
                userCalendarRepository.save(schedule);
                schedules.add(schedule);
            }
        }

        return schedules;
    }


    // Additional service methods...
}
