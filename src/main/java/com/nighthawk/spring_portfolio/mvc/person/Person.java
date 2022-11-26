package com.nighthawk.spring_portfolio.mvc.person;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name = "json", typeClass = JsonType.class)
public class Person {

    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // email, password, roles are key attributes to login and authentication
    @NotEmpty
    @Size(min = 5)
    @Column(unique = true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NonNull
    @Column(unique = true)
    private Integer steps = 0;

    @NonNull
    @Column(unique = true)
    private Integer averageSteps = 0;

    @NonNull
    @Column(unique = true)
    private Integer days = 0;

    // height in inches
    @NonNull
    @Column(unique = true)
    private Integer height = 0;

    // weight in kg
    @NonNull
    @Column(unique = true)
    private Integer weight = 0;

    // calories burned per day
    @NonNull
    @Column(unique = true)
    private double caloriesBurnedPerDay = 0;

    // @NonNull, etc placed in params of constructor: "@NonNull @Size(min = 2, max =
    // 30, message = "Name (2 to 30 chars)") String name"
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    /*
     * HashMap is used to store JSON for daily "stats"
     * "stats": {
     * "2022-11-13": {
     * "calories": 2200,
     * "steps": 8000
     * }
     * }
     */
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> stats = new HashMap<>();

    // Constructor used when building object from an API
    public Person(String email, String password, String name, int height, int weight, Date dob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.dob = dob;
    }

    // A custom getter to return age from dob attribute
    public int getAge() {
        if (this.dob != null) {
            LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return Period.between(birthDay, LocalDate.now()).getYears();
        }
        return -1;
    }

    public String emailToString() {
        return (("{ \"email\": " + this.email + " }"));
    }

    public String passwordToString() {
        return (("{ \"password\": " + this.password + " }"));
    }

    public String nameToString() {
        return (("{ \"name\": " + this.name + " }"));
    }

    public String dobToString() {
        return (("{ \"dob\": " + this.dob + " }"));
    }

    public static void main(String[] args) {
        // test zero arg constructr
        Person zeroArg = new Person();

        // test constructor with arguments
        Date date = new Date();
        Person personWithArgs = new Person("test@test.com", "password", "args", 67, 54, date);

        // test properties of zero arg constructor
        System.out.println("age: " + zeroArg.getAge());
        System.out.println("email: " + zeroArg.getEmail());
        System.out.println("password: " + zeroArg.getPassword());
        System.out.println("name: " + zeroArg.getName());
        // test properties of argument constructor
        System.out.println("age: " + personWithArgs.getAge());
        System.out.println("email: " + personWithArgs.getEmail());
        System.out.println("password: " + personWithArgs.getPassword());
        System.out.println("name: " + personWithArgs.getName());
    }
}