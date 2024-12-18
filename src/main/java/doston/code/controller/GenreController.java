package doston.code.controller;

import doston.code.dto.request.GenreRequestDTO;
import doston.code.dto.response.GenreResponseDTO;
import doston.code.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres(){

        return ResponseEntity.ok().body(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> getGenreById(@PathVariable("id") Long genreId){

        return ResponseEntity.ok().body(genreService.getGenreById(genreId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenreById(@PathVariable("id") Long genreId) {

            genreService.deleteGenreById(genreId);
            return ResponseEntity.noContent().build();
    }




}
