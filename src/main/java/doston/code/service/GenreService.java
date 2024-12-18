package doston.code.service;

import doston.code.dto.GenreRequestDTO;
import doston.code.dto.GenreResponseDTO;
import doston.code.entity.Genre;
import doston.code.entity.Profile;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.mapper.GenreMapper;
import doston.code.repository.GenreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper = GenreMapper.INSTANCE;


    public GenreResponseDTO createGenre(GenreRequestDTO genreRequestDTO) {

        String title = genreRequestDTO.title();
        validateGenreTitle(title);

        Genre newGenre = new Genre();
        newGenre.setTitle(title);
        newGenre.setCreatedDate(LocalDateTime.now());
        newGenre.setVisible(Boolean.TRUE);

        Genre savedGenre = genreRepository.save(newGenre);
        return genreMapper.toDto(savedGenre);
    }

    public GenreResponseDTO updateGenreById(Long id, GenreRequestDTO genreRequestDTO) {

        Genre oldGenre = getEntityById(id);

        String title = genreRequestDTO.title();
        validateGenreTitle(title);

        oldGenre.setTitle(title);
        oldGenre.setUpdatedDate(LocalDateTime.now());

        Genre updatedGenre = genreRepository.save(oldGenre);
        return genreMapper.toDto(updatedGenre);
    }

    private Boolean isTitleExist(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("genre cannot be null or empty");
        }
        return genreRepository.existsByTitle(title);
    }

    private Genre getEntityById(Long genreId) {
        if (genreId == null) {
            throw new IllegalArgumentException("Librarian ID cannot be null");
        }

        return genreRepository.findById(genreId)
                .orElseThrow(() -> new DataNotFoundException("Genre not found with ID: " + genreId));
    }

    private void validateGenreTitle(String title) {

        if (isTitleExist(title)) {
            throw new DataExistsException("Genre with title: " + title + " already exists");
        }
    }


}
