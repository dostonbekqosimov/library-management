package doston.code.controller;

import doston.code.dto.request.AuthorRequestDTO;
import doston.code.dto.response.AuthorResponseDTO;
import doston.code.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody @Valid AuthorRequestDTO authorRequestDTO) {
        AuthorResponseDTO author = authorService.createAuthor(authorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(
            @PathVariable("id") Long id,
            @RequestBody @Valid AuthorRequestDTO authorRequestDTO) {
        AuthorResponseDTO updatedAuthor = authorService.updateAuthorById(id, authorRequestDTO);
        return ResponseEntity.ok(updatedAuthor);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        return ResponseEntity.ok().body(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable("id") Long authorId) {
        return ResponseEntity.ok().body(authorService.getAuthorById(authorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable("id") Long authorId) {
        authorService.deleteAuthorById(authorId);
        return ResponseEntity.noContent().build();
    }
}
