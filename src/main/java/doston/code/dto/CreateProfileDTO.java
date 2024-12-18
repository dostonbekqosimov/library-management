package doston.code.dto;

import doston.code.enums.WorkTime;
import jakarta.validation.constraints.NotBlank;

public record CreateProfileDTO(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        String password,
        WorkTime workTime
) {
}