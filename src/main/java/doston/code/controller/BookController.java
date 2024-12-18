package doston.code.controller;

import doston.code.dto.request.BookRequestDTO;
import doston.code.dto.response.BookResponseDTO;
import doston.code.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(){

        List<BookResponseDTO> response = bookService.getAllBooks();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable("id") Long bookId){

        BookResponseDTO response = bookService.getBookById(bookId);
        return ResponseEntity.ok().body(response);
    }


}
