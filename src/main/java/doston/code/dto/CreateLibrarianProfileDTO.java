package doston.code.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateLibrarianProfileDTO(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Work time is required")
        String workTime
) {}