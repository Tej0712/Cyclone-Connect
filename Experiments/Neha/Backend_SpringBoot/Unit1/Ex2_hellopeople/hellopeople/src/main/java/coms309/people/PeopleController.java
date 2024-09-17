package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;




import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class PeopleController {

    // Note that there is only ONE instance of PeopleController in
    // Springboot system.
    HashMap<Long, Person> peopleList = new HashMap<>();


    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input.
    // Springboot automatically converts the list to JSON format
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/people")
    public Collection<Person> getAllPersons() {
        return peopleList.values();
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/people")
    public String createPerson(@RequestBody Person person) {
        Long id = peopleList.keySet().stream().max(Long::compare).orElse(0L) + 1;
        person.setId(id);
        peopleList.put(id, person);
        return "New person with ID " + id + " Saved";
    }



    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/people/{id}")
    public Person getPerson(@PathVariable Long id) {
        return peopleList.get(id);
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/people/{id}")
    public String updatePerson(@PathVariable Long id, @RequestBody Person person) {
        if (!peopleList.containsKey(id)) {
            return "Person with ID " + id + " not found.";
        }
        person.setId(id); // Ensure the person has the correct ID
        peopleList.put(id, person);
        return "Person with ID " + id + " updated.";
    }


    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method

    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable Long id) {
        if (peopleList.remove(id) != null) {
            return "Person with ID " + id + " deleted.";
        } else {
            return "Person with ID " + id + " not found.";
        }
    }

    @DeleteMapping("/people")
    public String deletePersons(@RequestBody List<Long> ids) {
        ids.forEach(id -> peopleList.remove(id));
        return "Persons deleted";
    }

    @GetMapping("/people/search")
    public List<Person> searchPersons(@RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String lastName,
                                      @RequestParam(required = false) String address,
                                      @RequestParam(required = false) String telephone,
                                      @RequestParam(required = false) String email) {
        return peopleList.values().stream()
                .filter(person -> (firstName == null || person.getFirstName().equals(firstName)) &&
                        (lastName == null || person.getLastName().equals(lastName)) &&
                        (address == null || person.getAddress().equals(address)) &&
                        (telephone == null || person.getTelephone().equals(telephone)) &&
                        (email == null || person.getEmail().equals(email)))
                .collect(Collectors.toList());
    }

}

