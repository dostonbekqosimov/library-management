package doston.code.mapper;

import doston.code.dto.request.LoanRequestDTO;
import doston.code.dto.response.LoanResponseDTO;
import doston.code.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "memberId", target = "memberId")
    @Mapping(source = "bookId", target = "bookId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "issueDate", target = "issueDate")
    @Mapping(source = "dueDate", target = "dueDate")
    @Mapping(source = "returnDate", target = "returnDate")
    LoanResponseDTO toDto(Loan loan);

    @Mapping(source = "memberId", target = "memberId")
    @Mapping(source = "bookId", target = "bookId")
    @Mapping(source = "issueDate", target = "issueDate")
    @Mapping(source = "dueDate", target = "dueDate")
    Loan toEntity(LoanRequestDTO dto);



}
