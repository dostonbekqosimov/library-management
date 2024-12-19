package doston.code.repository.custom;

import doston.code.dto.request.BookFilterDTO;
import doston.code.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomBookRepository {

    private final EntityManager entityManager;

    public CustomBookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> filter(BookFilterDTO filterDTO) {
        StringBuilder queryBuilder = new StringBuilder("SELECT b FROM Book b WHERE b.visible = true ");

        Map<String, Object> params = new HashMap<>();

        if (filterDTO.authorName() != null) {
            String authorName = filterDTO.authorName().toLowerCase();
            queryBuilder.append(" AND (LOWER(b.author.firstName) LIKE :authorName OR LOWER(b.author.lastName) LIKE :authorName) ");
            params.put("authorName", "%" + authorName + "%");
        }


        if (filterDTO.genreIds() != null && !filterDTO.genreIds().isEmpty()) {
            queryBuilder.append(" AND EXISTS (SELECT 1 FROM BookGenre bg WHERE bg.bookId = b.id AND bg.genreId IN :genreIds) ");
            params.put("genreIds", filterDTO.genreIds());
        }

        if (filterDTO.availability() != null) {
            switch (filterDTO.availability()) {
                case IN_STOCK -> queryBuilder.append(" AND b.count > 0 ");
                case OUT_OF_STOCK -> queryBuilder.append(" AND b.count = 0 ");
            }
        }


        Query query = entityManager.createQuery(queryBuilder.toString(), Book.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }

    public List<Book> searchBooks(String title, String author) {
        StringBuilder queryBuilder = new StringBuilder("SELECT b FROM Book b WHERE b.visible = true ");

        Map<String, Object> params = new HashMap<>();

        if (title != null) {
            queryBuilder.append(" AND LOWER(b.title) LIKE :title ");
            params.put("title", "%" + title.toLowerCase() + "%");
        }

        if (author != null) {
            String authorName = author.toLowerCase();
            queryBuilder.append(" AND (LOWER(b.author.firstName) LIKE :authorName OR LOWER(b.author.lastName) LIKE :authorName) ");
            params.put("authorName", "%" + authorName + "%");
        }

        Query query = entityManager.createQuery(queryBuilder.toString(), Book.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
