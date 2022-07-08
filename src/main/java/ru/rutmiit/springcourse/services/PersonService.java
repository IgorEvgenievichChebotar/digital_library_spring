package ru.rutmiit.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.springcourse.models.Book;
import ru.rutmiit.springcourse.models.Person;
import ru.rutmiit.springcourse.repositories.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> index() {
        return personRepository.findAll(Sort.by("name"));
    }

    @Transactional
    public void update(int personIdToSet, Person personToUpdate) {
        personToUpdate.setId(personIdToSet);
        personRepository.save(personToUpdate);
    }

    public Person show(int personId) {
        return personRepository.findById(personId).orElse(null);
    }

    @Transactional
    public void save(Person personToSave) {
        personRepository.save(personToSave);
    }

    @Transactional
    public void delete(int personId) {
        personRepository.deleteById(personId);
    }

    public List<Book> getBooksByPersonId(int personId) {

        List<Book> books = Objects.requireNonNull(
                personRepository.findById(personId).orElse(null)
        ).getBooks();

        books.forEach(book -> {
            if(book.getWhen_taken()!=null && new Date().getTime() - book.getWhen_taken().getTime() > 864000000)
                book.setExpired(true);
        });

        return books;
    }
}
