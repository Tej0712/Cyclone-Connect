/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;


import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @Modified By Tanmay Ghosh
 * @Modified By Vivek Bengre
 */
@RestController
class OwnerController {

    @Autowired
    OwnerRepository ownersRepository;

    private final Logger logger = LoggerFactory.getLogger(OwnerController.class);

    @RequestMapping(method = RequestMethod.POST, path = "/owners/new")
    public String saveOwner(@RequestBody Owners owner) {
        ownersRepository.save(owner);
        return "New Owner "+ owner.getFirstName() + " Saved";
    }
    // function just to create dummy data
    @RequestMapping(method = RequestMethod.GET, path = "/owner/create")
    public String createDummyData() {
        Owners o1 = new Owners(1, "John", "Doe", "404 Not found", "some numbers");
        Owners o2 = new Owners(2, "Jane", "Doe", "Its a secret", "you wish");
        Owners o3 = new Owners(3, "Some", "Pleb", "Right next to the Library", "515-345-41213");
        Owners o4 = new Owners(4, "Chad", "Champion", "Reddit memes corner", "420-420-4200");
        Owners o5 = new Owners(5, "Neha", "Tirunagiri", "Hyland Avenue", "515-815-000");
        ownersRepository.save(o1);
        ownersRepository.save(o2);
        ownersRepository.save(o3);
        ownersRepository.save(o4);
        ownersRepository.save(o5);

        return "Successfully created dummy data";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/owners")
    public List<Owners> getAllOwners() {
        logger.info("Entered into Controller Layer");
        List<Owners> results = ownersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/owners/{ownerId}")
    public Optional<Owners> findOwnerById(@PathVariable("ownerId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Owners> results = ownersRepository.findById(id);
        return results;
    }

    @DeleteMapping("/owners/{ownerId}")
    public ResponseEntity<String> deleteOwner(@PathVariable("ownerId") int id) {
        return ownersRepository.findById(id).map(owner -> {
            ownersRepository.delete(owner);
            logger.info("Deleted Owner with ID: " + id);
            return ResponseEntity.ok("Owner with ID " + id + " deleted successfully.");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/owners/{ownerId}") // This annotation maps PUT requests to this method
    public ResponseEntity<String> updateOwner(@PathVariable("ownerId") Integer ownerId, @RequestBody Owners updatedOwner) {
        return ownersRepository.findById(ownerId).map(owner -> {
            owner.setFirstName(updatedOwner.getFirstName());
            owner.setLastName(updatedOwner.getLastName());
            owner.setAddress(updatedOwner.getAddress());
            owner.setTelephone(updatedOwner.getTelephone());
            ownersRepository.save(owner);
            return ResponseEntity.ok("Owner " + owner.getFirstName() + " Updated");
        }).orElseGet(() -> {
            updatedOwner.setId(ownerId);
            ownersRepository.save(updatedOwner);
            return ResponseEntity.ok("New Owner Created with ID " + ownerId);
        });
    }

}
