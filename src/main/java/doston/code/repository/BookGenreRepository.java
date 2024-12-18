package doston.code.repository;

import doston.code.entity.BookGenre;
import org.springframework.data.repository.CrudRepository;

public interface BookGenreRepository extends CrudRepository<BookGenre, Long> {
}
