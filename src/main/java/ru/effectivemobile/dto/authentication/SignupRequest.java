package ru.effectivemobile.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Schema(description = "Signup request")
@Data
public class SignupRequest {

    @Schema(description = "Username", example = "SomeUsername")
    @NotNull(message = "Username cannot be null")
    @Length(min = 4, max = 256, message = "Username length must be between 4 and 256 characters")
    @JsonProperty("username")
    private String username;

    @Schema(description = "Password")
    @NotNull(message = "Password cannot be null")
    @Length(min = 8, max = 256, message = "Password length must be between 8 and 256 characters")
    @JsonProperty("password")
    private String password;

    @Schema(description = "E-mail")
    @NotNull(message = "E-mail cannot be null")
    @Email(message = "Incorrect email")
    @JsonProperty("email")
    private String email;

    @Schema(description = "phone")
    @NotNull(message = "Phone cannot be null")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    @JsonProperty("phone")
    private String phone;

    @Schema(description = "Full name", example = "James Bond")
    @NotNull(message = "Full name cannot be null")
    @JsonProperty("full_name")
    private String fullName;

    @Schema(description = "Date of birth", example = "1997-03-05")
    @NotNull(message = "Date of birth cannot be null")
    @JsonProperty("birth_date")
    private Date birthDate;

    @Schema(description = "Initial deposit", example = "1500")
    @NotNull(message = "Initial deposit cannot be null")
    @Min(value = 0, message = "Initial deposit must be non-negative")
    @JsonProperty("initial_deposit")
    private Double initialDeposit;
}
