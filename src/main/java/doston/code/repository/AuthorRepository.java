package doston.code.repository;

import doston.code.entity.Author;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAllBy();

    @Modifying
    @Transactional
    @Query("update Author a set a.visible = false where a.id = :authorId")
    void changeVisibility(Long authorId);

    Boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
