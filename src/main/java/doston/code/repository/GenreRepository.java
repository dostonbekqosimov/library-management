package doston.code.repository;

import doston.code.entity.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Boolean existsByTitle(String title);
}
