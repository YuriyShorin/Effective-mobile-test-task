package ru.effectivemobile.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Update email request dto")
@Data
@AllArgsConstructor
public class UpdateEmailRequest {

    @Schema(description = "E-mail")
    @NotNull(message = "E-mail cannot be null")
    @Email(message = "Incorrect email")
    @JsonProperty("old_email")
    private String oldEmail;

    @Schema(description = "E-mail")
    @NotNull(message = "E-mail cannot be null")
    @Email(message = "Incorrect email")
    @JsonProperty("new_email")
    private String newEmail;
}
