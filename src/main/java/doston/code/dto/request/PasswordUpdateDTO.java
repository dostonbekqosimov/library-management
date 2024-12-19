package doston.code.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateDTO(
        @NotBlank(message = "old password cannot be empty")
        String oldPassword,

        @NotBlank(message = "new password cannot be empty")
        String newPassword,

        @NotBlank(message = "confirm password cannot be empty")
        String confirmPassword
) {
}
