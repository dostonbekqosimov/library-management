package doston.code.service;

import doston.code.dto.request.BookRequestDTO;
import doston.code.dto.response.BookResponseDTO;
import doston.code.entity.Book;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.mapper.BookMapper;
import doston.code.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;

    public BookResponseDTO addBook(BookRequestDTO requestDTO) {

        validateRequestData(requestDTO, null);

        Book newBook = bookMapper.toEntity(requestDTO);

        newBook.setCreatedDate(LocalDateTime.now());
        newBook.setVisible(Boolean.TRUE);

        Book savedBook = bookRepository.save(newBook);
        return bookMapper.toDto(savedBook);
    }


    public BookResponseDTO updateBookById(Long bookId, BookRequestDTO requestDTO) {

        Book oldBook = getEntityById(bookId);

        validateRequestData(requestDTO, bookId);

        oldBook.setTitle(requestDTO.title());
        oldBook.setAuthorId(requestDTO.authorId());
        oldBook.setGenreId(requestDTO.genreId());
        oldBook.setCount(requestDTO.count());
        oldBook.setUpdatedDate(LocalDateTime.now());

        Book savedBook = bookRepository.save(oldBook);

        return bookMapper.toDto(savedBook);


    }

    public List<BookResponseDTO> getAllBooks() {

        List<Book> books = bookRepository.findAllBy();

        if (books.isEmpty()) {
            throw new DataNotFoundException("There is no available book");
        }

        return books.stream().map(bookMapper::toDto).toList();
    }


    private Book getEntityById(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }

        return bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book not found with ID: " + bookId));
    }


    private void validateRequestData(BookRequestDTO requestDTO, Long bookId) {


        Book existingBook = bookRepository.findByTitle(requestDTO.title());
        if (existingBook != null && (!existingBook.getId().equals(bookId))) {
            throw new DataExistsException("Book with title: " + requestDTO.title() + " already exists");
        }


        if (!authorService.existsById(requestDTO.authorId())) {
            throw new DataNotFoundException("Author not found with ID: " + requestDTO.authorId());
        }

        if (!genreService.existsById(requestDTO.genreId())) {
            throw new DataNotFoundException("Genre not found with ID: " + requestDTO.genreId());
        }
    }


}
