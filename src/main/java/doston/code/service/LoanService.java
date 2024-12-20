package doston.code.service;

import doston.code.dto.request.LoanRequestDTO;
import doston.code.dto.response.LoanResponseDTO;
import doston.code.entity.Book;
import doston.code.entity.Loan;
import doston.code.enums.LoanStatus;
import doston.code.exception.BadRequestException;
import doston.code.exception.BookUnavailableException;
import doston.code.exception.DataNotFoundException;
import doston.code.exception.MemberActiveLoanException;
import doston.code.mapper.LoanMapper;
import doston.code.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final MemberService memberService;
    private final LoanMapper loanMapper;

    public LoanResponseDTO issueLoan(LoanRequestDTO requestDTO) {


        validateMemberExists(requestDTO.memberId());
        validateBookAvailability(requestDTO.bookId());

        validateNoPreviousActiveLoan(requestDTO.memberId(), requestDTO.bookId());

        Loan newLoan = loanMapper.toEntity(requestDTO);
        newLoan.setStatus(LoanStatus.ISSUED);


        loanRepository.save(newLoan);

        return loanMapper.toDto(newLoan);
    }

    public LoanResponseDTO returnBook(Long loanId) {


        Loan existingLoan = getEntityById(loanId);


        existingLoan.setStatus(LoanStatus.RETURNED);
        existingLoan.setReturnDate(LocalDate.now());


        loanRepository.save(existingLoan);

        return loanMapper.toDto(existingLoan);
    }

    public List<LoanResponseDTO> getAllLoans() {
        List<Loan> loans = loanRepository.findAllBy();

        if (loans.isEmpty()) {
            throw new DataNotFoundException("No loans found");
        }

        return loans.stream().map(loanMapper::toDto).toList();
    }

    public LoanResponseDTO getLoanById(Long loanId) {
        Loan loan = getEntityById(loanId);
        return loanMapper.toDto(loan);
    }

    private Loan getEntityById(Long loanId) {
        if (loanId == null) {
            throw new IllegalArgumentException("Loan ID cannot be null");
        }

        return loanRepository.findById(loanId)
                .orElseThrow(() -> new DataNotFoundException("Loan not found with ID: " + loanId));
    }

    private void validateMemberExists(Long memberId) {
        if (!memberService.existsById(memberId)) {
            throw new DataNotFoundException("Member not found with ID: " + memberId);
        }
    }

    private void validateBookAvailability(Long bookId) {

        Book book = bookService.getEntityById(bookId);

        long activeLoans = loanRepository.countByBookIdAndStatus(bookId, LoanStatus.ISSUED);
        if (activeLoans >= book.getCount()) {
            throw new BookUnavailableException("Book is not available for loan");
        }
    }

    private void validateNoPreviousActiveLoan(Long memberId, Long bookId) {
        List<LoanResponseDTO> memberLoans = getMemberActiveLoans(memberId);

        boolean hasActiveLoanForBook = memberLoans.stream()
                .anyMatch(loan -> loan.bookId().equals(bookId));

        if (hasActiveLoanForBook) {
            throw new MemberActiveLoanException("Member already has an active loan for this book");
        }
    }
    public List<LoanResponseDTO> getMemberActiveLoans(Long memberId) {

        memberService.validateMemberExists(memberId);

        List<Loan> activeLoans = loanRepository.findByMemberIdAndStatus(memberId, LoanStatus.ISSUED);
        return activeLoans.stream().map(loanMapper::toDto).toList();
    }
}
