package ru.effectivemobile.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Update email request dto")
@Data
public class EmailRequest {

    @Schema(description = "E-mail")
    @NotNull(message = "E-mail cannot be null")
    @Email(message = "Incorrect email")
    private String email;
}
