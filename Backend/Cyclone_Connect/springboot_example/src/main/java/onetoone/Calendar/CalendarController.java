package onetoone.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarController(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @GetMapping
    public ResponseEntity<List<CalendarEntity>> getAllEvents() {
        List<CalendarEntity> events = calendarRepository.findAll();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarEntity> getEventById(@PathVariable("id") Long id) {
        Optional<CalendarEntity> eventOpt = calendarRepository.findById(id);
        return eventOpt.map(event -> ResponseEntity.ok(event))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CalendarEntity> createEvent(@RequestBody CalendarEntity event) {
        CalendarEntity savedEvent = calendarRepository.save(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") Long id, @RequestBody CalendarEntity updatedEvent) {
        return calendarRepository.findById(id).map(event -> {
            updatedEvent.setId(id);
            calendarRepository.save(updatedEvent);
            return ResponseEntity.ok().body("Event updated successfully");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        return calendarRepository.findById(id).map(event -> {
            calendarRepository.deleteById(id);
            return ResponseEntity.ok().body("Event deleted successfully");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
