package doston.code.service;

import doston.code.dto.request.AuthorRequestDTO;
import doston.code.dto.response.AuthorResponseDTO;
import doston.code.entity.Author;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.mapper.AuthorMapper;
import doston.code.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorResponseDTO createAuthor(AuthorRequestDTO authorRequestDTO) {

        String firstName = authorRequestDTO.firstName();
        String lastName = authorRequestDTO.lastName();
        validateAuthorName(firstName, lastName);

        Author newAuthor = authorMapper.toEntity(authorRequestDTO);

        newAuthor.setCreatedDate(LocalDateTime.now());
        newAuthor.setVisible(Boolean.TRUE);

        return authorMapper.toDto(newAuthor);
    }

    public AuthorResponseDTO updateAuthorById(Long id, AuthorRequestDTO authorRequestDTO) {
        Author oldAuthor = getEntityById(id);

        String firstName = authorRequestDTO.firstName();
        String lastName = authorRequestDTO.lastName();

        if (firstName.equals(oldAuthor.getFirstName()) && lastName.equals(oldAuthor.getLastName())) {
            return authorMapper.toDto(oldAuthor);
        }

        validateAuthorName(firstName, lastName);

        oldAuthor.setFirstName(firstName);
        oldAuthor.setLastName(lastName);
        oldAuthor.setUpdatedDate(LocalDateTime.now());

        return authorMapper.toDto(oldAuthor);
    }

    public List<AuthorResponseDTO> getAllAuthors() {

        // buyoqda har doim bitta user borligiga empty ga tekshirmadim.
        List<Author> authors = authorRepository.findAllBy();
        return authors.stream().map(authorMapper::toDto).toList();
    }

    public AuthorResponseDTO getAuthorById(Long authorId) {
        Author author = getEntityById(authorId);
        return authorMapper.toDto(author);
    }

    public void deleteAuthorById(Long authorId) {

        if (existsById(authorId)) {

            authorRepository.changeVisibility(authorId);

        } else {

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

        return authorRepository.existsByFirstNameAndLastName(firstName, lastName);
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