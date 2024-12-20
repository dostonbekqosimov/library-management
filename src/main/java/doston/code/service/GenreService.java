package doston.code.service;

import doston.code.dto.request.GenreRequestDTO;
import doston.code.dto.response.GenreResponseDTO;
import doston.code.entity.Genre;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.mapper.GenreMapper;
import doston.code.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;


    public GenreResponseDTO createGenre(GenreRequestDTO genreRequestDTO) {

        String title = genreRequestDTO.title();
        validateGenreTitle(title);

        Genre newGenre = new Genre();
        newGenre.setTitle(title);
        newGenre.setCreatedDate(LocalDateTime.now());
        newGenre.setVisible(Boolean.TRUE);

        genreRepository.save(newGenre);

        return genreMapper.toDto(newGenre);
    }

    public GenreResponseDTO updateGenreById(Long id, GenreRequestDTO genreRequestDTO) {

        Genre oldGenre = getEntityById(id);

        String title = genreRequestDTO.title();

        if (title.equals(oldGenre.getTitle())) {
            return genreMapper.toDto(oldGenre);
        }
        validateGenreTitle(title);

        oldGenre.setTitle(title);
        oldGenre.setUpdatedDate(LocalDateTime.now());

        genreRepository.save(oldGenre);

        return genreMapper.toDto(oldGenre);
    }

    public List<GenreResponseDTO> getAllGenres() {

        List<Genre> librarians = genreRepository.findAllByVisibleTrue();

        return librarians.stream().map(genreMapper::toDto).toList();
    }

    public GenreResponseDTO getGenreById(Long genreId) {

        Genre genre = getEntityById(genreId);

        return genreMapper.toDto(genre);

    }

    public void deleteGenreById(Long genreId) {

        if (existsById(genreId)) {
            genreRepository.changeVisibility(genreId);
        } else {
            throw new DataNotFoundException("Genre not found with ID: " + genreId);
        }


    }


    public Boolean existsById(Long genreId) {
        if (genreId == null) {
            throw new IllegalArgumentException("genre id cannot be null or empty");
        }
        return genreRepository.existsById(genreId);
    }

    public Boolean existsByIdList(List<Long> genreIdList) {
        if (genreIdList == null || genreIdList.isEmpty()) {
            throw new IllegalArgumentException("genre id list cannot be null or empty");
        }

        for (Long genreId : genreIdList) {
            if (!existsById(genreId)) {
                return false;
            }
        }
        return true;
    }



    private Boolean isTitleExist(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("genre title cannot be null or empty");
        }
        return genreRepository.existsByTitleAndVisibleTrue(title);
    }

    private Genre getEntityById(Long genreId) {
        if (genreId == null) {
            throw new IllegalArgumentException("Genre ID cannot be null");
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
