package doston.code.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthorResponseDTO(
        Long id,
        String firstName,
        String lastName,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
