package ru.rutmiit.digital_library_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rutmiit.digital_library_spring_boot.dao.PersonDAO;
import ru.rutmiit.digital_library_spring_boot.security.ClientDetails;
import ru.rutmiit.digital_library_spring_boot.services.ClientDetailsService;

import java.util.Optional;

@Controller
@RequestMapping("/general")
public class GeneralController {

    private final PersonDAO personDAO;
    private final ClientDetailsService clientDetailsService;

    @Autowired
    public GeneralController(PersonDAO personDAO, ClientDetailsService clientDetailsService) {
        this.personDAO = personDAO;
        this.clientDetailsService = clientDetailsService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personDAO.peopleAndTheirBooks());
        return "general/index";
    }

}
