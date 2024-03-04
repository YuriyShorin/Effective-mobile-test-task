package ru.effectivemobile.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Update phone request dto")
@Data
@AllArgsConstructor
public class UpdatePhoneRequest {

    @Schema(description = "phone")
    @NotNull(message = "Phone cannot be null")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    @JsonProperty("old_phone")
    private String oldPhone;

    @Schema(description = "phone")
    @NotNull(message = "Phone cannot be null")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    @JsonProperty("new_phone")
    private String newPhone;
}
