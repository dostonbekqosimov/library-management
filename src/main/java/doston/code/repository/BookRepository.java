package doston.code.repository;

import doston.code.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    Boolean existsByTitle(String title);

    Book findByTitle(String title);
}
