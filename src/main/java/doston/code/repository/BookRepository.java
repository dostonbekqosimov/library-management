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
    List<Book> findAllByVisibleTrue();

    Optional<Book> findByIdAndVisibleTrue(Long bookId);

    @Modifying
    @Transactional
    @Query("update Book b set b.visible = false where b.id = :bookId")
    void changeVisibility(@Param("bookId") Long bookId);



}
