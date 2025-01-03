package doston.code.dto.response;

import java.time.LocalDateTime;

public record LibrarianDTO(

        String id,
        String username,
        String role,
        String workTime,
        LocalDateTime createdDate
) {}
