package ru.effectivemobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effectivemobile.dto.user.EmailRequest;
import ru.effectivemobile.dto.user.PhoneRequest;
import ru.effectivemobile.dto.user.UpdateEmailRequest;
import ru.effectivemobile.dto.user.UpdatePhoneRequest;
import ru.effectivemobile.service.UserService;

@Tag(name = "User controller", description = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/phone")
    @Operation(summary = "Update phone")
    @ApiResponse(
            responseCode = "200",
            description = "Phone added")
    public ResponseEntity<?> addPhone(@RequestBody @Valid PhoneRequest phoneRequest) {
        return userService.addPhone(phoneRequest);
    }

    @PostMapping("/email")
    @Operation(summary = "Update email")
    @ApiResponse(
            responseCode = "200",
            description = "Email added")
    public ResponseEntity<?> addEmail(@RequestBody @Valid EmailRequest emailRequest) {
        return userService.addEmail(emailRequest);
    }

    @PutMapping("/phone")
    @Operation(summary = "Update phone")
    @ApiResponse(
            responseCode = "200",
            description = "Phone updated")
    public ResponseEntity<?> updatePhone(@RequestBody @Valid UpdatePhoneRequest updatePhoneRequest) {
        return userService.updatePhone(updatePhoneRequest);
    }

    @PutMapping("/email")
    @Operation(summary = "Update email")
    @ApiResponse(
            responseCode = "200",
            description = "Email updated")
    public ResponseEntity<?> updateEmail(@RequestBody @Valid UpdateEmailRequest updateEmailRequest) {
        return userService.updateEmail(updateEmailRequest);
    }

    @DeleteMapping("/phone")
    @Operation(summary = "Delete phone")
    @ApiResponse(
            responseCode = "200",
            description = "Phone deleted")
    public ResponseEntity<?> deletePhone(@RequestBody @Valid PhoneRequest phoneRequest) {
        return userService.deletePhone(phoneRequest);
    }

    @DeleteMapping("/email")
    @Operation(summary = "Delete email")
    @ApiResponse(
            responseCode = "200",
            description = "Email deleted")
    public ResponseEntity<?> deleteEmail(@RequestBody @Valid EmailRequest emailRequest) {
        return userService.deleteEmail(emailRequest);
    }
}
