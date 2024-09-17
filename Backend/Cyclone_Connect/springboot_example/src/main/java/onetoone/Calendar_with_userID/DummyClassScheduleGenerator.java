package onetoone.Calendar_with_userID;

import onetoone.Users.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyClassScheduleGenerator {

    // Assuming this method is part of your service layer
    public List<CalendarEntity> generateDummySchedulesForUser(User user) {
        // Fall semester dates (adjust as needed)
        LocalDate semesterStart = LocalDate.of(2024, 9, 1);
        LocalDate semesterEnd = LocalDate.of(2024, 12, 15);

        // Dummy courses - replace with real course names as needed
        String[] courses = {"Mathematics", "Physics", "Chemistry", "History", "English"};

        // Random generator for times and courses
        Random random = new Random();

        List<CalendarEntity> schedules = new ArrayList<>();
        LocalDate currentDay = semesterStart;

        // Generate a class schedule for each day of the semester (Monday to Friday)
        while (!currentDay.isAfter(semesterEnd)) {
            // Assuming classes occur from Monday to Friday
            if (currentDay.getDayOfWeek().getValue() >= 1 && currentDay.getDayOfWeek().getValue() <= 5) {
                CalendarEntity classEvent = new CalendarEntity();
                classEvent.setUser(user); // Associate with the user
                classEvent.setDate(currentDay);
                classEvent.setTitle(courses[random.nextInt(courses.length)]); // Randomly select a course

                // Generate random start and end times within typical class hours (8 AM to 3 PM)
                int startHour = 8 + random.nextInt(6); // Random start hour between 8 and 14 (2 PM)
                classEvent.setStartTime(LocalTime.of(startHour, 0)); // Classes start at the beginning of the hour
                classEvent.setEndTime(LocalTime.of(startHour + 1, 0)); // Assuming 1 hour duration for simplicity

                schedules.add(classEvent);
            }
            currentDay = currentDay.plusDays(1);
        }

        return schedules;
    }
}
