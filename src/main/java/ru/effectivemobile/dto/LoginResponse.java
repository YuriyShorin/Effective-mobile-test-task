package ru.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Login response dto")
@Data
@AllArgsConstructor
public class LoginResponse {

    @Schema(example = "Bearer")
    private final String type = "Bearer";

    @Schema(description = "Access token")
    private String accessToken;

    @Schema(description = "Refresh token")
    private String refreshToken;
}
