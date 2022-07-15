package ru.rutmiit.digital_library_spring_boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rutmiit.digital_library_spring_boot.models.Client;
import ru.rutmiit.digital_library_spring_boot.services.ClientDetailsService;

@Component
public class ClientValidator implements Validator {
    private final ClientDetailsService clientDetailsService;

    @Autowired
    public ClientValidator(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Client.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;

        try {
            clientDetailsService.loadUserByUsername(client.getUsername());
        } catch (UsernameNotFoundException ignored){
            return;
        }

        errors.rejectValue
                ("username", "", "Пользователь с таким именем пользователя уже существует");
    }
}
