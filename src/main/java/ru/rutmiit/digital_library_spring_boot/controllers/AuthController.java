package ru.rutmiit.digital_library_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rutmiit.digital_library_spring_boot.models.Client;
import ru.rutmiit.digital_library_spring_boot.services.ClientDetailsService;
import ru.rutmiit.digital_library_spring_boot.util.ClientValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ClientDetailsService clientDetailsService;
    private final ClientValidator clientValidator;

    @Autowired
    public AuthController(ClientDetailsService clientDetailsService, ClientValidator clientValidator) {
        this.clientDetailsService = clientDetailsService;
        this.clientValidator = clientValidator;
    }

    @GetMapping("login")
    public String showLoginPage(){
        return "auth/login";
    }

    @GetMapping("reg")
    public String showRegPage(@ModelAttribute("client") Client client){
        return "auth/reg";
    }

    @PostMapping("reg")
    public String performReg(@ModelAttribute("client") @Valid Client client,
                             BindingResult bindingResult){
        clientValidator.validate(client, bindingResult);

        if(bindingResult.hasErrors())
            return "/auth/reg";

        clientDetailsService.regClient(client);

        return "redirect:auth/login/";
    }
}
