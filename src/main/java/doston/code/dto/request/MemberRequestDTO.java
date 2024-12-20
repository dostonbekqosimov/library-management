package doston.code.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MemberRequestDTO(

        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Phone must not be blank")
        @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone must be a valid number between 10 to 15 digits")
        String phone,

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid")
        String email,

        @NotNull(message = "Membership date is required")
        @FutureOrPresent(message = "Membership date cannot be in the past")
        LocalDate membershipDate
) {
}
