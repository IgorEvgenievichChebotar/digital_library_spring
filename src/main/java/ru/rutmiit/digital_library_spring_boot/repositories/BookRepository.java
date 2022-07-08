package ru.rutmiit.digital_library_spring_boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rutmiit.digital_library_spring_boot.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> getBooksByOwnerId(int id);

    List<Book> findByNameStartingWithIgnoreCase(String name);
}
