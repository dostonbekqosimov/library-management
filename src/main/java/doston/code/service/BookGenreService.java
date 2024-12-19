package doston.code.service;

import doston.code.entity.BookGenre;
import doston.code.repository.BookGenreRepository;
import doston.code.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookGenreService {

    private final BookGenreRepository bookGenreRepository;
    private final GenreService genreService;

    public List<Long> getGenreIdList(Long bookId) {

        List<Long> genreIdList = bookGenreRepository.findAllGenresByBookId(bookId);

        return new ArrayList<>(genreIdList);
    }


    public void merge(Long bookId, List<Long> genreIdList) {
        if (genreIdList == null) {
            genreIdList = new ArrayList<>();
        }

        List<Long> oldGenreIdList = bookGenreRepository.findAllGenresByBookId(bookId);

        // Remove genres that are no longer present
        for (Long genreId : oldGenreIdList) {
            if (!genreIdList.contains(genreId)) {
                bookGenreRepository.deleteByBookIdAndGenreId(bookId, genreId);
            }
        }

        // Add new genres
        for (Long newGenreId : genreIdList) {
            if (!oldGenreIdList.contains(newGenreId)) {
                BookGenre entity = new BookGenre();
                entity.setBookId(bookId);
                entity.setGenreId(newGenreId);
                bookGenreRepository.save(entity);
            }
        }
    }

}
