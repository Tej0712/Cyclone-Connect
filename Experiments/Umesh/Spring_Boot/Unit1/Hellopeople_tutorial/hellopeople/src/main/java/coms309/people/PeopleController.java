package coms309.people;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;//package coms309.people;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.stream.Collectors;



import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class PeopleController {
    private Long nextId = 1L;


    //    // Note that there is only ONE instance of PeopleController in
    //    // Springboot system.
        HashMap<String, Person> peopleList = new  HashMap<>();
    //
    //    //CRUDL (create/read/update/delete/list)
    //    // use POST, GET, PUT, DELETE, GET methods for CRUDL
    //
    //    // THIS IS THE LIST OPERATION
    //    // gets all the people in the list and returns it in JSON format
    //    // This controller takes no input.
    //    // Springboot automatically converts the list to JSON format
    //    // in this case because of @ResponseBody
    //    // Note: To LIST, we use the GET method
    @GetMapping("/people")
    public  HashMap<String,Person> getAllPersons() {
        return peopleList;
    }
    //
    //    // THIS IS THE CREATE OPERATION
    //    // springboot automatically converts JSON input into a person object and
    //    // the method below enters it into the list.
    //    // It returns a string message in THIS example.
    //    // in this case because of @ResponseBody
    //    // Note: To CREATE we use POST method
    @PostMapping("/people")
    public String createPerson(@RequestBody Person person) {
        person.setId(nextId++); // Set the person's ID
        peopleList.put(person.getId().toString(), person);
        return "New person with ID " + person.getId() + " saved";
    }
    //
    //
    //    // THIS IS THE READ OPERATION
    //    // Springboot gets the PATHVARIABLE from the URL
    //    // We extract the person from the HashMap.
    //    // springboot automatically converts Person to JSON format when we return it
    //    // in this case because of @ResponseBody
    //    // Note: To READ we use GET method
    @GetMapping("/people/{id}")
    public Person getPerson(@PathVariable String id) {
        return peopleList.get(id);
    }
    //
    //
    //    // THIS IS THE UPDATE OPERATION
    //    // We extract the person from the HashMap and modify it.
    //    // Springboot automatically converts the Person to JSON format
    //    // Springboot gets the PATHVARIABLE from the URL
    //    // Here we are returning what we sent to the method
    //    // in this case because of @ResponseBody
    //    // Note: To UPDATE we use PUT method
    @PutMapping("/people/{id}")
    public Person updatePerson(@PathVariable String id, @RequestBody Person person) {
        peopleList.put(id, person);
        return person;
    }
    //
    //    // THIS IS THE DELETE OPERATION
    //    // Springboot gets the PATHVARIABLE from the URL
    //    // We return the entire list -- converted to JSON
    //    // in this case because of @ResponseBody
    //    // Note: To DELETE we use delete method
    //
    @DeleteMapping("/people/{firstName}")
    public String deletePerson(@PathVariable String firstName) {
        peopleList.remove(firstName);
        return "Person " + firstName + " deleted";
    }

    @DeleteMapping("/people")
    public String deletePersons(@RequestBody List<String> firstNames) {
        firstNames.forEach(peopleList::remove);
        return "Persons deleted";
    }

    // Search operation
    @GetMapping("/people/search")
    public List<Person> searchPersons(@RequestParam String searchTerm) {
        return peopleList.values().stream()
                .filter(person -> person.getFirstName().contains(searchTerm) || person.getLastName().contains(searchTerm))
                .collect(Collectors.toList());
    }

    // Batch update operation
    @PutMapping("/people/batch")
    public Map<String, String> updateMultiplePersons(@RequestBody List<Person> personsToUpdate) {
        Map<String, String> updateResults = new HashMap<>();
        for (Person person : personsToUpdate) {
            String id = person.getId().toString();
            if (peopleList.containsKey(id)) {
                peopleList.put(id, person);
                updateResults.put(id, "Updated successfully");
            } else {
                updateResults.put(id, "Person not found");
            }
        }
        return updateResults;
    }
}

