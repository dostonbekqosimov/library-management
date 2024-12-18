package doston.code.dto;

import jakarta.validation.constraints.NotBlank;

public record GenreRequestDTO(
        @NotBlank(message = "title is required")
        String title
) {}
