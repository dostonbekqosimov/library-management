package doston.code.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequestDTO(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName
) {
}

