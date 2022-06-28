package ru.rutmiit.model;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

public class Person {
    private int id;

    @Size(min = 2, max = 100, message = "Неправильный формат ФИО")
    String name;

    @Range(min = 1900, max = 2022, message = "Некорректный год рождения")
    int birthdate;

    public Person() {
    }

    public Person(String name, int birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(int birthdate) {
        this.birthdate = birthdate;
    }
}
