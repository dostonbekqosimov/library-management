package doston.code.repository;

import doston.code.entity.Loan;
import doston.code.enums.LoanStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findAllBy();

    long countByBookIdAndStatus(Long bookId, LoanStatus status);


    List<Loan> findByMemberIdAndStatus(Long memberId, LoanStatus loanStatus);
}
