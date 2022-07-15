package ru.rutmiit.digital_library_spring_boot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rutmiit.digital_library_spring_boot.models.Client;
import ru.rutmiit.digital_library_spring_boot.security.ClientDetails;

@Controller
@RequestMapping("/")
public class HomePageController {
    Client getClient(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails = (ClientDetails)authentication.getPrincipal();
        return clientDetails.getClient();
    }

    @GetMapping("/adminPage")
    public String showAdminPage(Model model){
        model.addAttribute("client", getClient());

        return "adminPage";
    }

    @GetMapping
    public String showHomePage(Model model){
        model.addAttribute("client", getClient());

        return "homePage";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo(Model model){
        model.addAttribute("client", getClient());

        return "showUserInfo";
    }
}
