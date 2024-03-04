package ru.effectivemobile.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.dto.authentication.JwtRequest;
import ru.effectivemobile.dto.authentication.LoginRequest;
import ru.effectivemobile.dto.authentication.LoginResponse;
import ru.effectivemobile.dto.authentication.SignupRequest;
import ru.effectivemobile.exception.UserAlreadyExistsException;
import ru.effectivemobile.exception.UserNotFoundException;
import ru.effectivemobile.exception.WrongPasswordException;
import ru.effectivemobile.exception.WrongRefreshTokenException;
import ru.effectivemobile.model.*;
import ru.effectivemobile.repository.AccountRepository;
import ru.effectivemobile.repository.EmailRepository;
import ru.effectivemobile.repository.PhoneRepository;
import ru.effectivemobile.repository.UserRepository;
import ru.effectivemobile.security.utils.JwtProvider;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final PhoneRepository phoneRepository;

    private final EmailRepository emailRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Transactional
    public ResponseEntity<?> signup(SignupRequest signupRequest) {
        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already exist");
        }

        if (emailRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with such email already exist");
        }

        if (phoneRepository.findByPhone(signupRequest.getPhone()).isPresent()) {
            throw new UserAlreadyExistsException("User with such phone already exist");
        }

        User user = new User(
                signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()),
                Set.of(new Email(signupRequest.getEmail())),
                Set.of(new Phone(signupRequest.getPhone())),
                signupRequest.getFullName(),
                signupRequest.getBirthDate(),
                Role.CUSTOMER
        );
        userRepository.save(user);

        user = userRepository.findByUsername(signupRequest.getUsername()).orElseThrow(UserNotFoundException::new);

        Account account = new Account(
                user.getId(),
                signupRequest.getInitialDeposit(),
                signupRequest.getInitialDeposit()
        );
        accountRepository.save(account);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        final User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<LoginResponse> getAccessToken(JwtRequest jwtRequest) {
        if (jwtProvider.validateRefreshToken(jwtRequest.getToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(jwtRequest.getToken());
            final String username = claims.getSubject();
            final User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
            final String saveRefreshToken = user.getRefreshToken();

            if (saveRefreshToken != null && saveRefreshToken.equals(jwtRequest.getToken())) {
                final String accessToken = jwtProvider.generateAccessToken(user);

                return ResponseEntity.ok(new LoginResponse(accessToken, null));
            }
        }

        return ResponseEntity.ok(new LoginResponse(null, null));
    }

    @Transactional
    public ResponseEntity<LoginResponse> getRefreshToken(JwtRequest jwtRequest) {
        if (jwtProvider.validateRefreshToken(jwtRequest.getToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(jwtRequest.getToken());
            final String username = claims.getSubject();
            final User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
            final String saveRefreshToken = user.getRefreshToken();

            if (saveRefreshToken != null && saveRefreshToken.equals(jwtRequest.getToken())) {

                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                user.setRefreshToken(newRefreshToken);
                userRepository.save(user);

                return ResponseEntity.ok(new LoginResponse(accessToken, newRefreshToken));
            }
        }

        throw new WrongRefreshTokenException();
    }
}
