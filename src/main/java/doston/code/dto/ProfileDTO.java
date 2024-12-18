package doston.code.dto;

import java.time.LocalDateTime;

public record ProfileDTO(

        String id,
        String username,
        String role,
        String workTime,
        LocalDateTime createdDate,
        boolean visible
) {}
