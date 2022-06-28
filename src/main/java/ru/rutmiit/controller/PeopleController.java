package ru.rutmiit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rutmiit.dao.BookDAO;
import ru.rutmiit.dao.PersonDAO;
import ru.rutmiit.model.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String indexPeople(Model model){
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", bookDAO.getBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){

        if (!bindingResult.hasErrors()) {
            personDAO.save(person);
            return "redirect:/people";
        }
        else{
            return "/people/new";
        }
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,
                               @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            personDAO.update(id, person);
            return "redirect:/people";
        } else{
            return "/people/edit";
        }
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
