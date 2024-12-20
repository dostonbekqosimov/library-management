package doston.code.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequestDTO(
        @NotBlank(message = "refresh token cannot be empty")
        String refreshToken
) {
}
