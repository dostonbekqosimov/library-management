package doston.code.repository;

import doston.code.entity.Genre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Boolean existsByTitle(String title);

    List<Genre> findAllBy();

    @Modifying
    @Transactional
    @Query("update Genre g set g.visible = false where g.id = :genreId")
    void changeVisibility(@Param("genreIdList") Long genreId);

}
