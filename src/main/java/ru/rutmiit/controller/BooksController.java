package ru.rutmiit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rutmiit.dao.BookDAO;
import ru.rutmiit.dao.PersonDAO;
import ru.rutmiit.model.Book;
import ru.rutmiit.model.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String indexBooks(Model model){
        model.addAttribute("books", bookDAO.index());
        return "/book/index";
    }

    @GetMapping("/{id}")
    public String showBook(Model model, @PathVariable int id, @ModelAttribute Person person){
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person> bookOwner = personDAO.getBookOwner(id);

        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        } else{
            model.addAttribute("people", personDAO.index());
        }

        return "/book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute Book book){
        return "/book/new";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable int id, Model model){
        model.addAttribute("book", bookDAO.show(id));
        return "/book/edit";
    }

    @PostMapping
    public String createBook(@ModelAttribute @Valid Book book,
                             BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            bookDAO.save(book);
            return "redirect:/books";
        } else{
            return "book/new";
        }

    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            bookDAO.update(id, book);
            return "redirect:/books";
        } else{
            return "/book/edit";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute Person selectedPerson){
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
