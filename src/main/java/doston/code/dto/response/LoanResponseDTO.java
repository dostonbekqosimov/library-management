package doston.code.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import doston.code.enums.LoanStatus;

import java.time.LocalDate;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoanResponseDTO(
          Long id ,
          Long memberId ,
          Long bookId ,
          LoanStatus status ,
          LocalDate issueDate ,
          LocalDate dueDate ,
          LocalDate returnDate
) {
}
