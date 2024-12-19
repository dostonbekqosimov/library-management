package doston.code.dto.request;

import doston.code.enums.WorkTime;
import jakarta.validation.constraints.NotBlank;

public record LibrarianRequestDTO(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        String password,
        WorkTime workTime
) {
}