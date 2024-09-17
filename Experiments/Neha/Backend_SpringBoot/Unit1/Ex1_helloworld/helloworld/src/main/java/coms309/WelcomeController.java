package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalTime;

@RestController
class mWelcomeController {

    @GetMapping("/")
    public String welcome() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.of(12, 0))) {
            return "Good morning and welcome to COMS 309";
        } else if (now.isBefore(LocalTime.of(18, 0))) {
            return "Good afternoon and welcome to COMS 309";
        } else {
            return "Good evening and welcome to COMS 309";
        }
    }
    @GetMapping("/quote")
    public String motivationalQuote() {
        String[] quotes = {
                "Believe in yourself! Have faith in your abilities!",
                "With hard work and dedication, you can achieve anything.",
                "The secret of getting ahead is getting started.",
                "It always seems impossible until it's done."
        };
        int index = new java.util.Random().nextInt(quotes.length);
        return quotes[index];
    }

    @GetMapping("/{name}")
    public String welcomeName(@PathVariable String name) {
        return "Hello and welcome to COMS 309, " + name + "!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(name = "mood", required = false) String mood) {
        if (mood != null) {
            return "You're feeling " + mood + " today!";
        } else {
            return "How are you feeling today?";
        }
    }

    @GetMapping("/greet/{language}")
    public String greetInLanguage(@PathVariable String language) {
        if (language.toLowerCase().equals("spanish")) {
            return "Hola y bienvenido a COMS 309";
        } else if (language.toLowerCase().equals("french")) {
            return "Bonjour et bienvenue à COMS 309";
        } else if (language.toLowerCase().equals("german")) {
            return "Hallo und willkommen bei COMS 309";
        } else if (language.toLowerCase().equals("malayalam")) {
            return "COMS 309 lekku swagatham";
        }
        return "Hello and welcome to COMS 309";
    }


}

