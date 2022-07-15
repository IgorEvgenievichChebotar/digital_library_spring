package ru.rutmiit.digital_library_spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.digital_library_spring_boot.models.Client;
import ru.rutmiit.digital_library_spring_boot.repositories.ClientRepository;
import ru.rutmiit.digital_library_spring_boot.security.ClientDetails;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientDetailsService(ClientRepository clientRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findByUsername(username);

        if(client.isEmpty())
            throw new UsernameNotFoundException("Пользователь с таким именем пользователя не найден");

        return new ClientDetails(client.get());
    }

    @Transactional
    public void regClient(Client client){
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRole("ROLE_USER");

        clientRepository.save(client);
    }
}
