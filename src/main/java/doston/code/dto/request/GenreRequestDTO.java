package doston.code.dto.request;

import jakarta.validation.constraints.NotBlank;

public record GenreRequestDTO(
        @NotBlank(message = "title is required")
        String title
) {}
