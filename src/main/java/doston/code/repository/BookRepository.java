package doston.code.repository;

import doston.code.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    Boolean existsByTitle(String title);

    Book findByTitle(String title);

    List<Book> findAllBy();

    Optional<Book> findByIdAndVisibleTrue(Long bookId);

    @Modifying
    @Transactional
    @Query("update Book b set b.visible = false where b.id = :bookId")
    void changeVisibility(@Param("bookId") Long bookId);


    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:author IS NULL OR " +
            " LOWER(CONCAT(b.author.firstName, ' ', b.author.lastName)) LIKE LOWER(CONCAT('%', :author, '%')))")
    List<Book> searchBooks(@Param("title") String title, @Param("author") String author);


}
