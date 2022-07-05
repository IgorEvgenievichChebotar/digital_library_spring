package ru.rutmiit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.model.Book;
import ru.rutmiit.model.Person;
import ru.rutmiit.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> index(){
        return bookRepository.findAll();
    }

    public Book show(int bookId) {
        return bookRepository.findById(bookId).orElse(null);
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

    public List<Book> getBooksByPersonId(int personId) {
        return bookRepository.getBooksByOwnerId(personId);
    }

    public Optional<Person> getBookOwner(int bookId){
        return bookRepository.findById(bookId).map(Book::getOwner);
    }

    @Transactional
    public void release(int bookId){
        bookRepository.findById(bookId).ifPresent(book -> book.setOwner(null));
    }

    @Transactional
    public void assign(int bookId, Person selectedPerson){
        bookRepository.findById(bookId).ifPresent(book -> book.setOwner(selectedPerson));
    }
}
