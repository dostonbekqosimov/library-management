package doston.code.controller;

import doston.code.dto.request.BookRequestDTO;
import doston.code.dto.response.BookResponseDTO;
import doston.code.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
