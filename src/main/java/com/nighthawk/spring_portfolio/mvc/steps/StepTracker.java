package com.nighthawk.spring_portfolio.mvc.steps;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.nighthawk.spring_portfolio.mvc.person.Person;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepTracker {
    @NonNull
    @Column(unique = true)
    private Integer steps = 0;

    @NonNull
    @Column(unique = true)
    private Integer averageSteps = 0;

    @NonNull
    private Person person;

    @NonNull
    private int days = 0;

    @NonNull
    private double caloriesBurned = 0;

    public StepTracker(Person person) {
        this.person = person;
        steps();
        activeDays();
        averageSteps();
    }

    // activeDays
    public int activeDays() {
        this.days += this.person.getStats().keySet().size();
        return this.days;
    }

    public String activeDaysToString() {
        return (("{ \"days\": " + this.days + " }"));
    }

    // total steps
    public int steps() {
        for (String i : this.person.getStats().keySet()) {
            Map<String, Object> currentStats = (Map<String, Object>) this.person.getStats().get(i);
            int statsSteps = (int) currentStats.get("steps");
            this.steps += statsSteps;
        }
        return this.steps;
    }

    public String stepsToString() {
        return (("{ \"steps\": " + this.steps + " }"));
    }

    // average steps
    public int averageSteps() {
        this.averageSteps = (int) (steps / days);
        return this.averageSteps;
    }

    public String averageStepsToString() {
        return (("{ \"averageSteps\": " + this.averageSteps + " }"));
    }

    public double caloriesBurned() {
        boolean isMale = this.person.getIsMale();
        double preliminaryCalculation = 0;
        double multiplier = 0;

        if (isMale) {
            preliminaryCalculation = (double) (66 + (6.2 * this.person.getWeight()) + (12.7 * this.person.getHeight())
                    - (6.76 * this.person.getAge()));
        } else {
            preliminaryCalculation = (double) (655.1 + (4.35 * this.person.getWeight())
                    + (4.7 * this.person.getHeight()) - (4.7 * this.person.getAge()));
        }

        // set multiplier values based on activity level
        if (averageSteps == 0) {
            multiplier = 1.2;
        } else if (averageSteps > 0 && averageSteps < 7500) {
            multiplier = 1.37;
        } else if (averageSteps > 7500 && averageSteps < 10000) {
            multiplier = 1.55;
        } else if (averageSteps > 1000 && averageSteps < 12500) {
            multiplier = 1.725;
        } else {
            multiplier = 1.9;
        }

        this.caloriesBurned = preliminaryCalculation * multiplier;
        return this.caloriesBurned;
    }

    public String caloriesBurnedToString() {
        return (("{ \"caloriesBurned\": " + this.caloriesBurned + " }"));
    }

    public static void main(String[] args) {
        Date date = new Date();
        Person testPerson = new Person("test@test.com", "password", "george", 67, 43, true, date);

        HashMap<String, String> stats = new HashMap<String, String>();

        stats.put("2022-11-25", "wow");

        StepTracker newStepTracker = new StepTracker(testPerson);

        newStepTracker.activeDays();
        newStepTracker.steps();
    }
}
