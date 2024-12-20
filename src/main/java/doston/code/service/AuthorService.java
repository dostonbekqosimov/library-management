package doston.code.service;

import doston.code.dto.request.AuthorRequestDTO;
import doston.code.dto.response.AuthorResponseDTO;
import doston.code.entity.Author;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.mapper.AuthorMapper;
import doston.code.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorResponseDTO createAuthor(AuthorRequestDTO authorRequestDTO) {
        log.info("Creating new author with name: {} {}", authorRequestDTO.firstName(), authorRequestDTO.lastName());

        String firstName = authorRequestDTO.firstName();
        String lastName = authorRequestDTO.lastName();
        validateAuthorName(firstName, lastName);

        Author newAuthor = authorMapper.toEntity(authorRequestDTO);

        newAuthor.setCreatedDate(LocalDateTime.now());
        newAuthor.setVisible(Boolean.TRUE);

        authorRepository.save(newAuthor);
        log.info("Author created successfully with ID: {}", newAuthor.getId());
        return authorMapper.toDto(newAuthor);
    }

    public AuthorResponseDTO updateAuthorById(Long id, AuthorRequestDTO authorRequestDTO) {

        log.info("Updating author with ID: {}", id);
        Author oldAuthor = getEntityById(id);

        String firstName = authorRequestDTO.firstName();
        String lastName = authorRequestDTO.lastName();

        if (firstName.equals(oldAuthor.getFirstName()) && lastName.equals(oldAuthor.getLastName())) {
            log.info("No changes detected for author with ID: {}", id);
            return authorMapper.toDto(oldAuthor);
        }

        validateAuthorName(firstName, lastName);

        oldAuthor.setFirstName(firstName);
        oldAuthor.setLastName(lastName);
        oldAuthor.setUpdatedDate(LocalDateTime.now());

        authorRepository.save(oldAuthor);
        log.info("Author updated successfully with ID: {}", oldAuthor.getId());
        return authorMapper.toDto(oldAuthor);
    }

    public List<AuthorResponseDTO> getAllAuthors() {

        log.info("Retrieving all authors");
        List<Author> authors = authorRepository.findAllByVisibleTrue();
        log.info("Retrieved {} authors", authors.size());
        return authors.stream().map(authorMapper::toDto).toList();
    }

    public AuthorResponseDTO getAuthorById(Long authorId) {

        log.info("Retrieving author with ID: {}", authorId);
        Author author = getEntityById(authorId);
        log.info("Author retrieved successfully: {}", author);
        return authorMapper.toDto(author);
    }

    public void deleteAuthorById(Long authorId) {

        log.info("Attempting to delete author with ID: {}", authorId);
        if (existsById(authorId)) {
            log.info("Author with ID: {} marked as invisible", authorId);
            authorRepository.changeVisibility(authorId);

        } else {
            log.warn("Attempt to delete non-existent author with ID: {}", authorId);
            throw new DataNotFoundException("Author not found with ID: " + authorId);
        }
    }

    public Boolean existsById(Long authorId) {

        if (authorId == null) {

            throw new IllegalArgumentException("Author ID cannot be null or empty");
        }
        return authorRepository.existsById(authorId);
    }

    private Boolean isAuthorExist(String firstName, String lastName) {

        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty()) {

            throw new IllegalArgumentException("Author name cannot be null or empty");
        }

        return authorRepository.existsByFirstNameAndLastNameAndVisibleTrue(firstName, lastName);
    }

    private Author getEntityById(Long authorId) {

        if (authorId == null) {

            throw new IllegalArgumentException("Author ID cannot be null");
        }

        return authorRepository.findById(authorId)

                .orElseThrow(() -> new DataNotFoundException("Author not found with ID: " + authorId));
    }

    private void validateAuthorName(String firstName, String lastName) {

        if (isAuthorExist(firstName, lastName)) {
            throw new DataExistsException("Author with name: " + firstName + " " + lastName + " already exists");
        }
    }
}