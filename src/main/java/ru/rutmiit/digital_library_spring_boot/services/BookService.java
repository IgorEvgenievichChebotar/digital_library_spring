package ru.rutmiit.digital_library_spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.digital_library_spring_boot.models.Book;
import ru.rutmiit.digital_library_spring_boot.models.Person;
import ru.rutmiit.digital_library_spring_boot.repositories.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> index(String sort_by){
        return bookRepository.findAll(Sort.by(Objects.requireNonNullElse(sort_by, "year")));
    }

    public List<Book> indexWithPaging(Integer page, Integer books_per_page, String sort_by) {
        return bookRepository.findAll(
                PageRequest.of(page, books_per_page, Sort.by(
                        Objects.requireNonNullElse(sort_by, "year"))
                )
        ).getContent();
    }

    public Book show(int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if(
                book.isPresent() &&
                book.get().getWhen_taken()!=null &&
                new Date().getTime() - book.get().getWhen_taken().getTime() > 864000000
        )
            book.get().setExpired(true);

        return book.orElse(null);
    }

    @Transactional
    public void update(Book bookToUpdate, int bookIdToSet){
        bookToUpdate.setId(bookIdToSet);
        bookRepository.save(bookToUpdate);
    }

    @Transactional
    public void delete(int bookId) {
        bookRepository.deleteById(bookId);
    }

    @Transactional
    public void save(Book bookToSave) {
        bookRepository.save(bookToSave);
    }

    public Optional<Person> getBookOwner(int bookId){
        return bookRepository.findById(bookId).map(Book::getOwner);
    }

    @Transactional
    public void release(int bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isPresent()){
            book.get().setOwner(null);
            book.get().setWhen_taken(null);
        }
    }

    @Transactional
    public void assign(int bookId, Person selectedPerson){
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            book.get().setOwner(selectedPerson);
            book.get().setWhen_taken(new Date());
        }
    }

    public List<Book> searchByName(String query){
        return bookRepository.findByNameStartingWithIgnoreCase(query);
    }

}
