package ru.rutmiit.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rutmiit.springcourse.dao.PersonDAO;

@Controller
@RequestMapping("/general")
public class GeneralController {

    private final PersonDAO personDAO;

    public GeneralController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personDAO.peopleAndTheirBooks());
        return "general/index";
    }
}
