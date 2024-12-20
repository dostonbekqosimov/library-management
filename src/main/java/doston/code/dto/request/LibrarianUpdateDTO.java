package doston.code.dto.request;

import doston.code.enums.WorkTime;
import jakarta.validation.constraints.NotBlank;

public record LibrarianUpdateDTO (
        @NotBlank(message = "username is required")
        String username,
        WorkTime workTime
){
}
