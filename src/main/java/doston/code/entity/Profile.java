package doston.code.entity;

import doston.code.enums.ProfileRole;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profile")
public class Profile {

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

    @Column(name = "work_time")
    private String workTime;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private Boolean visible;


}
