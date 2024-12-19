package doston.code.repository;

import doston.code.entity.BookGenre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookGenreRepository extends CrudRepository<BookGenre, Long> {


    @Query("select bg.genreId from BookGenre bg where bg.bookId = :bookId")
    List<Long> findAllGenresByBookId(@Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query("delete from BookGenre bg where bg.bookId = :bookId and bg.genreId = :genreId")
    void deleteByBookIdAndGenreId(@Param("bookId") Long bookId, @Param("genreId") Long genreId);


}
