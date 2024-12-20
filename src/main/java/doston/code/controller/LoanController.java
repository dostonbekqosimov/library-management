package doston.code.controller;

import doston.code.dto.request.LoanRequestDTO;
import doston.code.dto.response.LoanResponseDTO;
import doston.code.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/issue")
    public ResponseEntity<LoanResponseDTO> issueLoan(@RequestBody @Valid LoanRequestDTO requestDTO) {
        LoanResponseDTO response = loanService.issueLoan(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanResponseDTO> returnBook(@PathVariable("id") Long loanId) {
        LoanResponseDTO response = loanService.returnBook(loanId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<LoanResponseDTO> response = loanService.getAllLoans();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable("id") Long loanId) {
        LoanResponseDTO response = loanService.getLoanById(loanId);
        return ResponseEntity.ok().body(response);
    }
}