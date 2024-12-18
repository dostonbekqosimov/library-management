package doston.code.service;

import doston.code.dto.request.BookRequestDTO;
import doston.code.dto.response.BookResponseDTO;
import doston.code.entity.Book;
import doston.code.entity.Book;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.mapper.BookMapper;
import doston.code.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;

    public BookResponseDTO addBook(BookRequestDTO requestDTO) {

        validateRequestData(requestDTO);

        Book newBook = bookMapper.toEntity(requestDTO);

        newBook.setCreatedDate(LocalDateTime.now());
        newBook.setVisible(Boolean.TRUE);

        Book savedBook = bookRepository.save(newBook);
        return bookMapper.toDto(savedBook);
    }

    private Boolean isTitleExist(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        return bookRepository.existsByTitle(title);
    }

    private Book getEntityById(Long BookId) {
        if (BookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }

        return bookRepository.findById(BookId)
                .orElseThrow(() -> new DataNotFoundException("Book not found with ID: " + BookId));
    }

    private void validateBookTitle(String title) {

        if (isTitleExist(title)) {
            throw new DataExistsException("Book with title: " + title + " already exists");
        }
    }


    private void validateRequestData(BookRequestDTO requestDTO) {

        validateBookTitle(requestDTO.title());

        if (!authorService.existsById(requestDTO.authorId())) {
            throw new DataNotFoundException("Author not found with ID: " + requestDTO.authorId());
        }

        if (!genreService.existsById(requestDTO.genreId())) {
            throw new DataNotFoundException("Genre not found with ID: " + requestDTO.genreId());
        }
    }
}
