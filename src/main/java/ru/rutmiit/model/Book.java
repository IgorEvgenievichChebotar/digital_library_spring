package ru.rutmiit.model;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty
    @Size(min = 1, max=100, message = "Некорректное название")
    private String name;
    @NotEmpty
    @Size(min = 2, max=100, message = "Некорректный автор")
    private String author;
    @Range(min=1000, max=2022, message = "Неправильный формат даты. Пример: 1985")
    private int year;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
