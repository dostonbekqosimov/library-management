package doston.code.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookResponseDTO(
        Long id,
        String title,
        Long authorId,
        List<Long> genreIdList,
        Integer count,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
