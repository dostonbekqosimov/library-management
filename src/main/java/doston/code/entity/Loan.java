package doston.code.entity;

import doston.code.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Column(name = "book_id")
    private Long bookId;

    @ManyToOne
    @JoinColumn(name = "book_id", updatable = false, insertable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private LocalDate returnDate;


}
