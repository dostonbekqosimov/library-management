package doston.code.dto.response;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshResponseDTO(

        String accessToken,
        String refreshToken
) {
}
