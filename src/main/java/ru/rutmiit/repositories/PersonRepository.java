package ru.rutmiit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rutmiit.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
