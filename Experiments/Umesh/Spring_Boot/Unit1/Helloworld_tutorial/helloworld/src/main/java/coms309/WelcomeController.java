package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;


@RestController
public class WelcomeController {

    private static final Map<DayOfWeek, String> dayMessages = new HashMap<>();
    private static final Map<String, String> weatherSimulator = new HashMap<>();

    private static final List<String> motivationalQuotes = List.of(
            "Believe you can and you're halfway there.",
            "The only way to do great work is to love what you do.",
            "Success is not final, failure is not fatal: It is the courage to continue that counts.",
            "You are never too old to set another goal or to dream a new dream.",
            "It does not matter how slowly you go as long as you do not stop."
    );
    private static final Random random = new Random();



    static {
        dayMessages.put(DayOfWeek.MONDAY, "Start your week with enthusiasm!");
        dayMessages.put(DayOfWeek.TUESDAY, "Keep the momentum going!");
        dayMessages.put(DayOfWeek.WEDNESDAY, "Halfway to the weekend!");
        dayMessages.put(DayOfWeek.THURSDAY, "The weekend is almost here!");
        dayMessages.put(DayOfWeek.FRIDAY, "Time to plan for the weekend!");
        dayMessages.put(DayOfWeek.SATURDAY, "Enjoy your weekend!");
        dayMessages.put(DayOfWeek.SUNDAY, "Rest up for the week ahead!");

        weatherSimulator.put("Ames", "Sunny, 24°C");
        weatherSimulator.put("Des Moines", "Cloudy, 19°C");
        weatherSimulator.put("Chicago", "Rainy, 16°C");

    }

    @GetMapping("/")
    public String welcome() {
        LocalDateTime now = LocalDateTime.now();
        String greeting = getGreeting(now);
        return " Welcome to COMS 309. " + greeting + " " +  dayMessages.get(now.getDayOfWeek());
    }

    @GetMapping("/{name}")
    public String personalizedWelcome(@PathVariable String name, @RequestParam(required = false) String title) {
        LocalDateTime now = LocalDateTime.now();
        String greeting = getGreeting(now);
        String fullName = (title != null && !title.isEmpty()) ? title + " " + name : name;
        String quoteOfTheDay = getMotivationalQuote();
        return "Welcome to COMS 309: " + greeting + " " + fullName + ". " + dayMessages.get(now.getDayOfWeek()) + " Quote of the day: " + quoteOfTheDay;
    }

    private String getGreeting(LocalDateTime now) {
        int hour = now.getHour();
        if (hour >= 5 && hour < 12) return "Good morning"; // Morning is from 5:00 AM to 11:59 AM
        else if (hour >= 12 && hour < 17) return "Good afternoon"; // Afternoon is from 12:00 PM to 4:59 PM
        else if (hour >= 17 && hour < 21) return "Good evening"; // Evening is from 5:00 PM to 8:59 PM
        else return "Good night"; // Night is from 9:00 PM to 4:59 AM
    }

    @GetMapping("/weather/{city}")
    public String welcomeWithWeather(@PathVariable String city) {
        LocalDateTime now = LocalDateTime.now();
        String greeting = getGreeting(now);
        String weather = weatherSimulator.getOrDefault(city, "Weather data not available for " + city);
        return "Welcome to COMS 309. " + greeting + " " + dayMessages.get(now.getDayOfWeek()) + ". Current weather in " + city + ": " + weather;
    }

    private String getMotivationalQuote() {
        int index = random.nextInt(motivationalQuotes.size());
        return motivationalQuotes.get(index);
    }

}
