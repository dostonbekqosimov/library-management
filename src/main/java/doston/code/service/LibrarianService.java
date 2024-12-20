package doston.code.service;

import doston.code.dto.request.LibrarianUpdateDTO;
import doston.code.dto.request.PasswordUpdateDTO;
import doston.code.dto.request.LibrarianRequestDTO;
import doston.code.dto.response.LibrarianDTO;
import doston.code.entity.Librarian;
import doston.code.enums.ProfileRole;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.exception.ForbiddenException;
import doston.code.mapper.LibrarianMapper;
import doston.code.repository.LibrarianRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static doston.code.util.SpringSecurityUtil.getCurrentUserId;
import static doston.code.util.SpringSecurityUtil.getCurrentUserRole;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibrarianService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final LibrarianRepository librarianRepository;
    private final LibrarianMapper librarianMapper;


    public LibrarianDTO createLibrarianProfile(LibrarianRequestDTO requestDTO) {

        validateRequestData(requestDTO);

        Librarian librarian = librarianMapper.toEntity(requestDTO);

        librarian.setPassword(passwordEncoder.encode(requestDTO.password()));
        librarian.setRole(ProfileRole.ROLE_LIBRARIAN);
        librarian.setCreatedDate(LocalDateTime.now());
        librarian.setVisible(true);

        librarianRepository.save(librarian);

        return librarianMapper.toDto(librarian);
    }

    public List<LibrarianDTO> getAllLibrarians() {

        List<Librarian> librarians = librarianRepository.findAllByVisibleTrue();

        return librarians.stream().map(librarianMapper::toDto).toList();


    }

    public LibrarianDTO getById(Long librarianId) {

        Librarian librarian = getEntityById(librarianId);

        return librarianMapper.toDto(librarian);


    }

    public String changePassword(PasswordUpdateDTO request, Long librarianId) {

        Long currentUserId = getCurrentUserId();

        if (librarianId != null) {

            validateAdminAccess();

            Librarian entity = getEntityById(librarianId);
            validatePasswordChange(request, entity);
            entity.setPassword(passwordEncoder.encode(request.newPassword()));
            librarianRepository.save(entity);
        } else {

            Librarian entity = getEntityById(currentUserId);

            if (!currentUserId.equals(entity.getId())) {
                throw new ForbiddenException("Unauthorized action: You cannot change another librarian's password.");
            }

            validatePasswordChange(request, entity);
            entity.setPassword(passwordEncoder.encode(request.newPassword()));
            librarianRepository.save(entity);

        }
        return "Password changed successfully";
    }

    public LibrarianDTO updateLibrarianDetails(LibrarianUpdateDTO request, Long librarianId) {

        Librarian librarian = getEntityById(librarianId);

        validateUpdateData(request, librarian);

        librarian.setUsername(request.username());
        librarian.setWorkTime(request.workTime());
        librarian.setUpdatedDate(LocalDateTime.now());

        librarianRepository.save(librarian);

        return librarianMapper.toDto(librarian);


    }

    public void deleteLibrarianById(Long librarianId) {

        validateAdminAccess();

        if (existsById(librarianId)) {
            librarianRepository.changeVisibility(librarianId);

        } else {
            throw new DataNotFoundException("Librarian not found with ID: " + librarianId);

        }
    }


    private void validateAdminAccess() {
        if (getCurrentUserRole() != ProfileRole.ROLE_ADMIN) {
            throw new ForbiddenException("You aren't authorized to do this.");
        }

    }


    private Boolean isUserNameExist(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        return librarianRepository.existsByUsernameAndVisibleTrue(userName);
    }

    public Boolean existsById(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("book id cannot be null or empty");
        }
        return librarianRepository.existsById(bookId);
    }

    private Librarian getEntityById(Long librarianId) {
        if (librarianId == null) {
            throw new IllegalArgumentException("Librarian ID cannot be null");
        }

        return librarianRepository.findByIdAndVisibleTrue(librarianId)
                .orElseThrow(() -> new DataNotFoundException("Librarian not found with ID: " + librarianId));
    }

    private void validateUpdateData(LibrarianUpdateDTO requestDTO, Librarian existingLibrarian) {

        if (!existingLibrarian.getUsername().equals(requestDTO.username())) {
            if (isUserNameExist(requestDTO.username())) {
                throw new DataExistsException("Librarian with username " + requestDTO.username() + " already exists");
            }
        }
        if (requestDTO.workTime() == null) {
            throw new IllegalArgumentException("working time cannot be null or empty");
        }

    }

    private void validateRequestData(LibrarianRequestDTO requestDTO) {

        if (isUserNameExist(requestDTO.username())) {
            throw new DataExistsException("Librarian with username " + requestDTO.username() + " already exists");
        }

        if (requestDTO.workTime() == null) {
            throw new IllegalArgumentException("working time cannot be null or empty");
        }

    }

    private void validatePasswordChange(PasswordUpdateDTO request, Librarian entity) {
        if (!passwordEncoder.matches(request.oldPassword(), entity.getPassword())) {
            throw new IllegalArgumentException("Old password incorrect");
        }

        if (passwordEncoder.matches(request.newPassword(), entity.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("Passwords didn't match");
        }
    }


}
