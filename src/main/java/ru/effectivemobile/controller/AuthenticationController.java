package ru.effectivemobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effectivemobile.dto.JwtRequest;
import ru.effectivemobile.dto.LoginRequest;
import ru.effectivemobile.dto.LoginResponse;
import ru.effectivemobile.dto.SignupRequest;
import ru.effectivemobile.service.AuthenticationService;

@Tag(name = "Authentication controller", description = "Authentication API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Signup")
    @ApiResponse(
            responseCode = "201",
            description = "User created")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest) {
        return authenticationService.signup(signupRequest);
    }

    @Operation(summary = "Login")
    @ApiResponse(
            responseCode = "200",
            description = "Login successful",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))})
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @Operation(summary = "Generate new access token")
    @ApiResponse(
            responseCode = "200",
            description = "Access token was generated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))})
    @PostMapping("/access")
    public ResponseEntity<LoginResponse> getAccessToken(@RequestBody @Valid JwtRequest jwtRequest) {
        return authenticationService.getAccessToken(jwtRequest);
    }

    @Operation(summary = "Generate new refresh token")
    @ApiResponse(
            responseCode = "200",
            description = "Refresh token was generated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))})
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> getRefreshToken(@RequestBody @Valid JwtRequest jwtRequest) {
        return authenticationService.getRefreshToken(jwtRequest);
    }
}
