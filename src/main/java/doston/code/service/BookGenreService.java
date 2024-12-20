package doston.code.service;

import doston.code.entity.BookGenre;
import doston.code.repository.BookGenreRepository;
import doston.code.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookGenreService {

    private final BookGenreRepository bookGenreRepository;
    private final GenreService genreService;

    public List<Long> getGenreIdList(Long bookId) {

        List<Long> genreIdList = bookGenreRepository.findAllGenresByBookId(bookId);

        return new ArrayList<>(genreIdList);
    }


    public void merge(Long bookId, List<Long> genreIdList) {
        log.info("Merging genres for book ID: {}. New genre list size: {}", bookId, genreIdList != null ? genreIdList.size() : 0);

        if (genreIdList == null) {
            genreIdList = new ArrayList<>();
        }

        List<Long> oldGenreIdList = bookGenreRepository.findAllGenresByBookId(bookId);

        for (Long genreId : oldGenreIdList) {
            if (!genreIdList.contains(genreId)) {
                bookGenreRepository.deleteByBookIdAndGenreId(bookId, genreId);
                log.debug("Removing genre ID: {} from book ID: {}", genreId, bookId);
            }
        }

        for (Long newGenreId : genreIdList) {
            if (!oldGenreIdList.contains(newGenreId)) {
                BookGenre entity = new BookGenre();
                entity.setBookId(bookId);
                entity.setGenreId(newGenreId);
                bookGenreRepository.save(entity);
                log.debug("Adding new genre ID: {} to book ID: {}", newGenreId, bookId);
            }
        }
        log.info("Successfully completed genre merge for book ID: {}", bookId);
    }

}
