package ru.rutmiit.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rutmiit.model.Book;
import ru.rutmiit.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, birthdate=? WHERE id=?",
                person.getName(), person.getBirthdate(), id);
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(name, birthdate) VALUES (?, ?)",
                person.getName(), person.getBirthdate());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("select person.* from book join person on person.id = book.person_id where book.id=?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
}
