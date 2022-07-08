package ru.rutmiit.springcourse.models;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min = 1, max=100, message = "Некорректное название")
    private String name;

    @NotEmpty
    @Size(min = 2, max=100, message = "Некорректный автор")
    private String author;

    @Range(min=1000, max=2022, message = "Неправильный формат даты. Пример: 1985")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Temporal(TemporalType.TIMESTAMP)
    private Date when_taken;

    @Transient
    private boolean expired;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Date getWhen_taken() {
        return when_taken;
    }

    public void setWhen_taken(Date whenTaken) {
        this.when_taken = whenTaken;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString(){
        return String.format("%s (%d) - %s", name, year, author);
    }
}
