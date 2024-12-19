package doston.code.service;

import doston.code.dto.request.PasswordUpdateDTO;
import doston.code.dto.request.ProfileRequestDTO;
import doston.code.dto.response.ProfileDTO;
import doston.code.entity.Profile;
import doston.code.enums.ProfileRole;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.exception.ForbiddenException;
import doston.code.mapper.ProfileMapper;
import doston.code.repository.ProfileRepository;
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
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;


    public ProfileDTO createLibrarianProfile(ProfileRequestDTO requestDTO) {

        validateRequestData(requestDTO);

        Profile profile = profileMapper.toEntity(requestDTO);

        profile.setPassword(passwordEncoder.encode(requestDTO.password()));
        profile.setRole(ProfileRole.ROLE_LIBRARIAN);
        profile.setCreatedDate(LocalDateTime.now());
        profile.setVisible(true);

        return profileMapper.toDto(profile);
    }

    public List<ProfileDTO> getAllLibrarians() {

        List<Profile> librarians = profileRepository.findAllBy();

        return librarians.stream().map(profileMapper::toDto).toList();


    }

    public ProfileDTO getById(Long librarianId) {

        Profile profile = getEntityById(librarianId);

        return ProfileMapper.INSTANCE.toDto(profile);


    }

    public String changeOwnPassword(PasswordUpdateDTO request) {

        Long currentUserId = getCurrentUserId();

        Profile entity = getEntityById(currentUserId);

        validatePasswordChange(request, entity);
        entity.setPassword(passwordEncoder.encode(request.newPassword()));
        profileRepository.save(entity);

        return "Password changed successfully";
    }

    public String resetPasswordByAdmin(PasswordUpdateDTO request, Long librarianId) {

        validateAdminAccess();

        Profile entity = getEntityById(librarianId);

        validatePasswordChange(request, entity);

        entity.setPassword(passwordEncoder.encode(request.newPassword()));
        profileRepository.save(entity);
        return "Password changed successfully";

    }

    public void deleteLibrarianById(Long librarianId) {

        validateAdminAccess();

        if (existsById(librarianId)) {
            profileRepository.changeVisibility(librarianId);

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
        return profileRepository.existsByUsername(userName);
    }

    public Boolean existsById(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("book id cannot be null or empty");
        }
        return profileRepository.existsById(bookId);
    }

    private Profile getEntityById(Long librarianId) {
        if (librarianId == null) {
            throw new IllegalArgumentException("Librarian ID cannot be null");
        }

        return profileRepository.findById(librarianId)
                .orElseThrow(() -> new DataNotFoundException("Librarian not found with ID: " + librarianId));
    }

    private void validateRequestData(ProfileRequestDTO requestDTO) {

        if (isUserNameExist(requestDTO.username())) {
            throw new DataExistsException("Librarian with username " + requestDTO.username() + " already exists");
        }

        if (requestDTO.workTime() == null) {
            throw new IllegalArgumentException("working time cannot be null or empty");
        }

    }

    private void validatePasswordChange(PasswordUpdateDTO request, Profile entity) {
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
