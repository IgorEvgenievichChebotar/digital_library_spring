package ru.rutmiit.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rutmiit.model.Book;
import ru.rutmiit.model.Person;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("select * from book where id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void edit(Book book, int id){
        jdbcTemplate.update("update book set name=?, author=?, year=? where id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("update book set name=?, author=?, year=? where id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id=?", id);
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(name, author, year) VALUES (?,?,?)",
                book.getName(),book.getAuthor(),book.getYear());
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("select * from book where person_id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void release(int id){
        jdbcTemplate.update("update book set person_id=null where book.id=?", id);
    }

    public void assign(int id, Person selectedPerson){
        jdbcTemplate.update("update book set person_id=? where book.id = ?", selectedPerson.getId() ,id);
    }
}
