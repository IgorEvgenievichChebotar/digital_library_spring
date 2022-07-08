package ru.rutmiit.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rutmiit.springcourse.models.Person;
import ru.rutmiit.springcourse.services.BookService;
import ru.rutmiit.springcourse.services.PersonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public PeopleController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personService.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.show(id));
        model.addAttribute("books", personService.getBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.show(id));
        return "people/edit";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){

        if (!bindingResult.hasErrors()) {
            personService.save(person);
            return "redirect:/people";
        }
        else{
            return "/people/new";
        }
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            personService.update(id, person);
            return "redirect:/people";
        } else{
            return "/people/edit";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/people";
    }
}
