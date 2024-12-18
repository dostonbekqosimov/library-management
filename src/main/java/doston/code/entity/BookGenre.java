package doston.code.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book_genre")
public class BookGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "genre_id", nullable = false)
    private Long genreId;


    @ManyToOne
    @JoinColumn(name = "book_id", updatable = false, insertable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "genre_id", updatable = false, insertable = false)
    private Genre genre;




}
