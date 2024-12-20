package doston.code.entity;

import doston.code.enums.ProfileRole;
import doston.code.enums.WorkTime;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "librarian")
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50)
    private ProfileRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_time")
    private WorkTime workTime;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    private Boolean visible;


}
