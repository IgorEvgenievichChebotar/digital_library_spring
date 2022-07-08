package ru.rutmiit.digital_library_spring_boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rutmiit.digital_library_spring_boot.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
