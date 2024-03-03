package ru.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Schema(description = "Signup request")
@Data
public class SignupRequest {

    @Schema(description = "E-mail")
    @NotNull(message = "E-mail cannot be null")
    @Email(message = "Incorrect email")
    private String email;

    @Schema(description = "Password")
    @NotNull(message = "Password cannot be null")
    @Length(min = 8, max = 256, message = "Password length must be between 8 and 256 characters")
    private String password;

    @Schema(description = "phone")
    @NotNull(message = "Phone cannot be null")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    private String phone;

    @Schema(description = "fullName", example = "James Bond")
    @NotNull(message = "Full name cannot be null")
    private String fullName;
}
