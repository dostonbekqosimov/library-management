package doston.code.dto;



import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GenreResponseDTO(
        Long id,
        String title,
        LocalDateTime createdDate,
        LocalDateTime updatedDate) {

}

