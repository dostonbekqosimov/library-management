package doston.code.controller;

import doston.code.dto.GenreRequestDTO;
import doston.code.dto.GenreResponseDTO;
import doston.code.entity.Genre;
import doston.code.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<GenreResponseDTO> createGenre(@RequestBody @Valid GenreRequestDTO genreRequestDTO) {
        GenreResponseDTO genre = genreService.createGenre(genreRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> updateGenre(
            @PathVariable Long id,
            @RequestBody @Valid GenreRequestDTO genreRequestDTO) {
        GenreResponseDTO updatedGenre = genreService.updateGenreById(id, genreRequestDTO);
        return ResponseEntity.ok(updatedGenre);
    }



}
