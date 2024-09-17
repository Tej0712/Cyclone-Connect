package coms309.people;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
public class PeopleController {
    private Map<Long, Person> people = new ConcurrentHashMap<>();
    private long currentId = 1;

    // Paginated listing of people
    @GetMapping("/people")
    public List<Person> listPeople(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return people.values().stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    // Bulk creation of people
    @PostMapping("/people/batch")
    public String addPeople(@RequestBody List<Person> newPeople) {
        newPeople.forEach(person -> {
            person.setId(currentId++);
            people.put(person.getId(), person);
        });
        return newPeople.size() + " people added.";
    }

    // Update address and telephone of a person (partial update)
    @PatchMapping("/people/{id}")
    public String updatePersonContactInfo(@PathVariable Long id, @RequestParam Optional<String> address, @RequestParam Optional<String> telephone) {
        Person person = people.get(id);
        if (person == null) {
            return "Person not found.";
        }
        address.ifPresent(person::setAddress);
        telephone.ifPresent(person::setTelephone);
        return "Updated person with ID " + id;
    }

    // Finding people by combination of attributes
    @GetMapping("/people/search")
    public List<Person> searchPersons(@RequestParam Optional<String> firstName, @RequestParam Optional<String> lastName) {
        return people.values().stream()
                .filter(person -> firstName.map(fName -> person.getFirstName().contains(fName)).orElse(true) &&
                        lastName.map(lName -> person.getLastName().contains(lName)).orElse(true))
                .collect(Collectors.toList());
    }

    // Finding people by their address
    @GetMapping("/people/address")
    public List<Person> findPeopleByAddress(@RequestParam String address) {
        return people.values().stream()
                .filter(person -> person.getAddress().contains(address))
                .collect(Collectors.toList());
    }
}
