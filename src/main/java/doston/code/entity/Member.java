package doston.code.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;


    @Column(name = "membership_date")
    private LocalDate membershipDate;

    @Column(name = "create_by_id")
    private Long createdById;

    @ManyToOne
    @JoinColumn(name = "created_by_id", updatable = false, insertable = false)
    private Librarian librarian;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Column(name = "visible")
    private Boolean visible;
}
