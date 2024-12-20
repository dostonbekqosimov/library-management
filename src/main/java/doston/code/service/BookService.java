package doston.code.service;

import doston.code.dto.request.BookFilterDTO;
import doston.code.dto.request.BookRequestDTO;
import doston.code.dto.response.BookResponseDTO;
import doston.code.entity.Book;
import doston.code.exception.DataNotFoundException;
import doston.code.mapper.BookMapper;
import doston.code.repository.BookRepository;
import doston.code.repository.custom.CustomBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final CustomBookRepository customBookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookGenreService bookGenreService;
    private final BookMapper bookMapper;

    // bunda 2 Book object yaratilinyabdi, shuni keyinroq optimize qilish kerak
    public BookResponseDTO addBook(BookRequestDTO requestDTO) {

        validateRequestData(requestDTO);
        log.info("Adding new book with title: {}", requestDTO.title());
        Book newBook = bookMapper.toEntity(requestDTO);

        newBook.setCreatedDate(LocalDateTime.now());
        newBook.setVisible(Boolean.TRUE);

        bookRepository.save(newBook);
        Long newBookId = newBook.getId();
        log.info("Successfully added book with ID: {}", newBookId);

        bookGenreService.merge(newBookId, requestDTO.genreIdList());


        return bookMapper.toDto(newBook);

    }


    public BookResponseDTO updateBookById(Long bookId, BookRequestDTO requestDTO) {
        log.info("Updating book with ID: {}", bookId);

        Book oldBook = getEntityById(bookId);

        validateRequestData(requestDTO);

        oldBook.setTitle(requestDTO.title());
        oldBook.setAuthorId(requestDTO.authorId());
        oldBook.setCount(requestDTO.count());
        oldBook.setUpdatedDate(LocalDateTime.now());

        bookRepository.save(oldBook);
        log.debug("Successfully updated book with ID: {}", bookId);

        bookGenreService.merge(bookId, requestDTO.genreIdList());

        return bookMapper.toDto(oldBook);


    }

    public List<BookResponseDTO> getAllBooks() {

        List<Book> books = bookRepository.findAllByVisibleTrue();

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
        log.debug("Searching books with title: {} and author: {}", title, author);

        if (title == null && author == null) {
            throw new IllegalArgumentException("At least title or author name should be given");
        }

        List<Book> books = customBookRepository.searchBooks(title, author);

        if (books.isEmpty()) {
            log.debug("No books found matching title: {} and author: {}", title, author);
            throw new DataNotFoundException("No matched book found");
        }
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }


    public List<BookResponseDTO> filterBooks(BookFilterDTO filterDTO) {

        log.debug("Filtering books with criteria: {}", filterDTO);


        List<Book> books = customBookRepository.filter(filterDTO);

        if (books.isEmpty()) {
            return List.of();
        }

        return books.stream()
                .map(bookMapper::toDto)
                .toList();


    }

    public void deleteBookById(Long bookId) {

        if (existsById(bookId)) {
            log.info("Attempting to delete book with ID: {}", bookId);
            bookRepository.changeVisibility(bookId);
            log.info("Successfully deleted book with ID: {}", bookId);
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


    public Book getEntityById(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }

        return bookRepository.findByIdAndVisibleTrue(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book not found with ID: " + bookId));
    }


    private void validateRequestData(BookRequestDTO requestDTO) {

        log.debug("Validating book request data: {}", requestDTO);

        if (!authorService.existsById(requestDTO.authorId())) {
            log.warn("Author not found with ID: {}", requestDTO.authorId());
            throw new DataNotFoundException("Author not found with ID: " + requestDTO.authorId());
        }

        if (!genreService.existsByIdList(requestDTO.genreIdList())) {
            log.warn("One or more genres not found for IDs: {}", requestDTO.genreIdList());
            throw new DataNotFoundException("One or more genres not found for IDs: " + requestDTO.genreIdList());
        }

    }


}
