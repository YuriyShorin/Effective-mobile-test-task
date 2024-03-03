package ru.effectivemobile.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.effectivemobile.exception.UserNotFoundException;
import ru.effectivemobile.model.User;
import ru.effectivemobile.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}