package ru.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Schema(description = "Login request dto")
@Data
public class LoginRequest {

    @Schema(description = "E-mail")
    @NotNull(message = "E-mail cannot be null")
    @Email(message = "Incorrect email")
    private String email;

    @Schema(description = "Password")
    @NotNull(message = "Password cannot be null")
    @Length(min = 8, max = 256, message = "Password length must be between 8 and 256 characters")
    private String password;
}
