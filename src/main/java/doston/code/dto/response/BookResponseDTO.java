package doston.code.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookResponseDTO(
        Long id,
        String title,
        Long authorId,
        Long genreId,
        Integer count,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
