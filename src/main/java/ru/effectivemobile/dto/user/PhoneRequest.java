package ru.effectivemobile.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(description = "Update email request dto")
@Data
public class PhoneRequest {

    @Schema(description = "phone")
    @NotNull(message = "Phone cannot be null")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    private String phone;
}
