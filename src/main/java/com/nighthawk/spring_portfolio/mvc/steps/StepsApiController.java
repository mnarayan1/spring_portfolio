package com.nighthawk.spring_portfolio.mvc.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;

import java.util.*;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/steps")
public class StepsApiController {
    /*
     * #### RESTful API ####
     * Resource: https://spring.io/guides/gs/rest-service/
     */

    // Autowired enables Control to connect POJO Object through JPA
    @Autowired
    private PersonJpaRepository repository;

    /*
     * GET List of People
     */
    @GetMapping("/activeDays/{id}")
    public ResponseEntity<String> getActiveDays(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) { // Good ID
            Person person = optional.get(); // value from findByID
            StepTracker newTracker = new StepTracker(person);
            newTracker.activeDays();
            return new ResponseEntity<>(newTracker.activeDaysToString(), HttpStatus.OK); // OK HTTP response: status
            // code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/totalSteps/{id}")
    public ResponseEntity<String> getTotalSteps(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) { // Good ID
            Person person = optional.get(); // value from findByID
            StepTracker newTracker = new StepTracker(person);
            newTracker.steps();
            return new ResponseEntity<>(newTracker.stepsToString(), HttpStatus.OK); // OK HTTP response: status
                                                                                    // code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/averageSteps/{id}")
    public ResponseEntity<String> getAverageSteps(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) { // Good ID
            Person person = optional.get(); // value from findByID
            StepTracker newTracker = new StepTracker(person);
            newTracker.steps();
            newTracker.activeDays();
            newTracker.averageSteps();
            return new ResponseEntity<>(newTracker.averageStepsToString(), HttpStatus.OK); // OK HTTP response: status
            // code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/updatePerson/{id}")
    public ResponseEntity<Object> postPerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) { // Good ID
            Person person = optional.get(); // value from findByID
            StepTracker newTracker = new StepTracker(person);

            // update person with step tracker calculations
            person.setSteps(newTracker.steps());
            person.setDays(newTracker.activeDays());
            person.setAverageSteps(newTracker.averageSteps());
            repository.save(person);
            return new ResponseEntity<>("Updated " + person.getName() + " successfully!", HttpStatus.OK); // OK HTTP
                                                                                                          // response:
                                                                                                          // status
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
