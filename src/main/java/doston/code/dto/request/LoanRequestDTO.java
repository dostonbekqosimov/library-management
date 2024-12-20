package doston.code.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LoanRequestDTO(
        @NotNull(message = "Member ID must not be null")
        Long memberId,

        @NotNull(message = "Book ID must not be null")
        Long bookId,

        @NotNull(message = "Issue date must not be null")
        @FutureOrPresent(message = "Issue date must be today or a future date")
        LocalDate issueDate,

        @NotNull(message = "Due date must not be null")
        @FutureOrPresent(message = "Due date must be today or a future date")
        LocalDate dueDate
) {
}
