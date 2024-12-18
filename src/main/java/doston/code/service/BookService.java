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
import java.util.stream.Collectors;

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

    public BookResponseDTO getBookById(Long bookId) {

        Book book = getEntityById(bookId);

        return bookMapper.toDto(book);

    }

    public List<BookResponseDTO> searchBooks(String title, String author) {

        if (title == null && author == null) {
            throw new IllegalArgumentException("At least title or author name should be given");
        }
        List<Book> books = bookRepository.searchBooks(title, author);

        if (books.isEmpty()) {
            throw new DataNotFoundException("No matched book found");
        }
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }


    public void deleteBookById(Long bookId) {

        if (existsById(bookId)) {
            bookRepository.changeVisibility(bookId);
        } else {
            throw new DataNotFoundException("Book not found with ID: " + bookId);
        }
    }

    public Boolean existsById(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("book id cannot be null or empty");
        }
        return bookRepository.existsById(bookId);
    }


    private Book getEntityById(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }

        return bookRepository.findByIdAndVisibleTrue(bookId)
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
