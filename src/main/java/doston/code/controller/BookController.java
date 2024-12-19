package doston.code.controller;

import doston.code.dto.request.BookFilterDTO;
import doston.code.dto.request.BookRequestDTO;
import doston.code.dto.response.BookResponseDTO;
import doston.code.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@RequestBody @Valid BookRequestDTO requestDTO) {

        BookResponseDTO response = bookService.addBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBookById(@PathVariable("id") Long bookId,
                                                          @RequestBody @Valid BookRequestDTO requestDTO) {

        BookResponseDTO response = bookService.updateBookById(bookId, requestDTO);
        return ResponseEntity.ok().body(response);

    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {

        List<BookResponseDTO> response = bookService.getAllBooks();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable("id") Long bookId) {

        BookResponseDTO response = bookService.getBookById(bookId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDTO>> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author) {

        List<BookResponseDTO> response = bookService.searchBooks(title, author);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BookResponseDTO>> filterArticles(
            @RequestBody BookFilterDTO filterDTO) {

        List<BookResponseDTO> books = bookService.filterBooks(filterDTO);
        return ResponseEntity.ok().body(books);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable("id") Long bookId) {

        bookService.deleteBookById(bookId);
        return ResponseEntity.noContent().build();
    }


}
