package ru.effectivemobile.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.effectivemobile.dto.JwtRequest;
import ru.effectivemobile.dto.LoginRequest;
import ru.effectivemobile.dto.LoginResponse;
import ru.effectivemobile.dto.SignupRequest;
import ru.effectivemobile.exception.UserAlreadyExistsException;
import ru.effectivemobile.exception.UserNotFoundException;
import ru.effectivemobile.exception.WrongPasswordException;
import ru.effectivemobile.exception.WrongRefreshTokenException;
import ru.effectivemobile.model.Role;
import ru.effectivemobile.model.User;
import ru.effectivemobile.repository.UserRepository;
import ru.effectivemobile.security.utils.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public ResponseEntity<?> signup(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = new User(
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getPhone(),
                signupRequest.getFullName(),
                Role.CUSTOMER
        );
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        final User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
    }

    public ResponseEntity<LoginResponse> getAccessToken(JwtRequest jwtRequest) {
        if (jwtProvider.validateRefreshToken(jwtRequest.getToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(jwtRequest.getToken());
            final String email = claims.getSubject();
            final User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
            final String saveRefreshToken = user.getRefreshToken();

            if (saveRefreshToken != null && saveRefreshToken.equals(jwtRequest.getToken())) {
                final String accessToken = jwtProvider.generateAccessToken(user);

                return ResponseEntity.ok(new LoginResponse(accessToken, null));
            }
        }

        return ResponseEntity.ok(new LoginResponse(null, null));
    }

    public ResponseEntity<LoginResponse> getRefreshToken(JwtRequest jwtRequest) {
        if (jwtProvider.validateRefreshToken(jwtRequest.getToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(jwtRequest.getToken());
            final String email = claims.getSubject();
            final User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
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
