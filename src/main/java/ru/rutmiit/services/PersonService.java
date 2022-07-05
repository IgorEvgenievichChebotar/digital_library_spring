package ru.rutmiit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.model.Person;
import ru.rutmiit.repositories.PersonRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> index() {
        return personRepository.findAll();
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
}
