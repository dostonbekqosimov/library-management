package doston.code.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateLibrarianProfileDTO(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        String password,
        @NotBlank(message = "worktime is required")
        String workTime
) {}