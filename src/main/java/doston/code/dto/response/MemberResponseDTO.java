package doston.code.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberResponseDTO (
        Long id,
        String name,
        String phone,
        String email,
        LocalDate membershipDate,
        Long createdById,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
){
}
