package ru.rutmiit.digital_library_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rutmiit.digital_library_spring_boot.models.Book;
import ru.rutmiit.digital_library_spring_boot.models.Person;
import ru.rutmiit.digital_library_spring_boot.services.BookService;
import ru.rutmiit.digital_library_spring_boot.services.PersonService;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page",required = false) Integer books_per_page,
                        @RequestParam(value = "sort_by",required = false) String sort_by
    ){
        if(page!=null & books_per_page!=null)
            model.addAttribute("books", bookService.indexWithPaging(page, books_per_page, sort_by));
        else {
            model.addAttribute("books", bookService.index(sort_by));
        }
        return "/books/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable int id, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookService.show(id));

        Optional<Person> bookOwner = bookService.getBookOwner(id);

        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        } else{
            model.addAttribute("people", personService.index());
        }

        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "/books/new";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable int id, Model model){
        model.addAttribute("book", bookService.show(id));
        return "/books/edit";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            bookService.save(book);
            return "redirect:/books";
        } else{
            return "books/new";
        }

    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            bookService.update(book, id);
            return "redirect:/books";
        } else{
            return "/books/edit";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "/books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam(value = "query") String query){
        model.addAttribute("foundedBooks", bookService.searchByName(query));
        return "/books/search";
    }
}
