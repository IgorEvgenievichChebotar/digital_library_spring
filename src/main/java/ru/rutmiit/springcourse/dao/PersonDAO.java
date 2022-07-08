package ru.rutmiit.springcourse.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rutmiit.springcourse.models.Book;
import ru.rutmiit.springcourse.models.Person;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class PersonDAO {
    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Set<Person> peopleAndTheirBooks(){
        Session session = entityManager.unwrap(Session.class);

        Set<Person> people = new HashSet<Person>(
                session.createQuery("select p from Person p left join fetch p.books").getResultList());

        people.forEach(person -> {
            for(Book book : person.getBooks()){
                if(book.getWhen_taken()!=null && new Date().getTime() - book.getWhen_taken().getTime() > 864000000)
                    book.setExpired(true);
            }
        });

        return people;
    }
}

