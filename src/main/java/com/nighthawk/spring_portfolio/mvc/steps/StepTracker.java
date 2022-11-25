package com.nighthawk.spring_portfolio.mvc.steps;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.TypeDef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.vladmihalcea.hibernate.type.json.JsonType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepTracker {
    @NonNull
    @Column(unique = true)
    private Integer steps = 0;

    @NonNull
    private Person person;

    @NonNull
    private int days = 0;

    public StepTracker(Person person) {
        this.person = person;
    }

    public int activeDays() {
        for (String i : this.person.getStats().keySet()) {
            this.days++;
        }
        System.out.println(days);
        return this.days;
    }

    public String activeDaysToString() {
        return (("{ \"days\": " + this.days + " }"));
    }

    public static void main(String[] args) {
        Date date = new Date();
        Person testPerson = new Person("test@test.com", "password", "george", date);

        HashMap<String, String> stats = new HashMap<String, String>();

        stats.put("2022-11-25", "wow");

        StepTracker newStepTracker = new StepTracker(testPerson);

        newStepTracker.activeDays();
    }
}
