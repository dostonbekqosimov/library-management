package doston.code.dto.request;

import java.time.LocalDate;

public record MemberRequestDTO(

        String name,
        String phone,
        String email,
        LocalDate membershipDate
) {
}
