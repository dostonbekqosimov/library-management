package doston.code.service;

import doston.code.repository.BookGenreRepository;
import doston.code.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookGenreService {

    private final BookGenreRepository bookGenreRepository;

}
