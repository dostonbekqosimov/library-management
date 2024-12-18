package doston.code.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequestDTO(

        @NotBlank(message = "Title must not be blank")
        String title,

        @NotNull(message = "Author is required")
        Long authorId,

        @NotNull(message = "Genre is required")
        Long genreId,

        @NotNull(message = "Count is required")
        @Min(value = 0, message = "Count must be greater than or equal to 0")
        Integer count

) {
}

