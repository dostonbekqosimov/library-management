package doston.code.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false, insertable = false)
    private Author author;

    @Column(name = "genre_id", nullable = false)
    private Long genreId;

    @ManyToOne
    @JoinColumn(name = "genre_id", updatable = false, insertable = false)
    private Genre genre;

    @Column(name = "count")
    private Integer count;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Column(name = "visible")
    private Boolean visible;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookGenre> bookGenres = new ArrayList<>();


}
