package ru.effectivemobile.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.dto.user.EmailRequest;
import ru.effectivemobile.dto.user.PhoneRequest;
import ru.effectivemobile.dto.user.UpdateEmailRequest;
import ru.effectivemobile.dto.user.UpdatePhoneRequest;
import ru.effectivemobile.exception.*;
import ru.effectivemobile.model.Email;
import ru.effectivemobile.model.JwtAuthentication;
import ru.effectivemobile.model.Phone;
import ru.effectivemobile.model.User;
import ru.effectivemobile.repository.EmailRepository;
import ru.effectivemobile.repository.PhoneRepository;
import ru.effectivemobile.repository.UserRepository;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private EmailRepository emailRepository;

    private PhoneRepository phoneRepository;

    @Override
    public User loadUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public ResponseEntity<?> addPhone(PhoneRequest phoneRequest) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if (phoneRepository.findByPhone(phoneRequest.getPhone()).isPresent()) {
            throw new UserAlreadyExistsException("User with such phone already exist");
        }

        User user = userRepository.findByUsername(authentication.getUsername()).orElseThrow(UserNotFoundException::new);
        Set<Phone> phones = user.getPhones();
        phones.add(new Phone(phoneRequest.getPhone()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> addEmail(EmailRequest emailRequest) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if (emailRepository.findByEmail(emailRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with such email already exist");
        }

        User user = userRepository.findByUsername(authentication.getUsername()).orElseThrow(UserNotFoundException::new);
        Set<Email> emails = user.getEmails();
        emails.add(new Email(emailRequest.getEmail()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> updatePhone(UpdatePhoneRequest updatePhoneRequest) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if (phoneRepository.findByPhone(updatePhoneRequest.getNewPhone()).isPresent()) {
            throw new UserAlreadyExistsException("User with such phone already exist");
        }

        User user = userRepository.findByUsername(authentication.getUsername()).orElseThrow(UserNotFoundException::new);
        Set<Phone> phones = user.getPhones();

        if (!phones.contains(new Phone(updatePhoneRequest.getOldPhone()))) {
            throw new PhoneNotFoundException();
        }
        phones.remove(new Phone(updatePhoneRequest.getOldPhone()));
        phones.add(new Phone(updatePhoneRequest.getNewPhone()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> updateEmail(UpdateEmailRequest updateEmailRequest) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if (emailRepository.findByEmail(updateEmailRequest.getNewEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with such phone already exist");
        }

        User user = userRepository.findByUsername(authentication.getUsername()).orElseThrow(UserNotFoundException::new);
        Set<Email> emails = user.getEmails();

        if (!emails.contains(new Email(updateEmailRequest.getOldEmail()))) {
            throw new EmailNotFoundException();
        }
        emails.remove(new Email(updateEmailRequest.getOldEmail()));
        emails.add(new Email(updateEmailRequest.getNewEmail()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> deletePhone(PhoneRequest phoneRequest) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getUsername()).orElseThrow(UserNotFoundException::new);
        Set<Phone> phones = user.getPhones();

        if (phones.size() == 1) {
            throw new CannotDeleteException("You need to have at least one phone");
        }
        if (!phones.contains(new Phone(phoneRequest.getPhone()))) {
            throw new PhoneNotFoundException();
        }

        phones.remove(new Phone(phoneRequest.getPhone()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> deleteEmail(EmailRequest emailRequest) {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getUsername()).orElseThrow(UserNotFoundException::new);
        Set<Email> emails = user.getEmails();

        if (emails.size() == 1) {
            throw new CannotDeleteException("You need to have at least one email");
        }
        if (!emails.contains(new Email(emailRequest.getEmail()))) {
            throw new EmailNotFoundException();
        }

        emails.remove(new Email(emailRequest.getEmail()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}