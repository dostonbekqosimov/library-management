package doston.code.service;

import doston.code.dto.CreateProfileDTO;
import doston.code.dto.ProfileDTO;
import doston.code.entity.Profile;
import doston.code.enums.ProfileRole;
import doston.code.enums.WorkTime;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.mapper.ProfileMapper;
import doston.code.repository.ProfileRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibrarianService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper = ProfileMapper.INSTANCE;


    public ProfileDTO createLibrarianProfile(@Valid CreateProfileDTO profileDTO) {

        String username = profileDTO.username();
        if (isUserNameExist(username)) {
            throw new DataExistsException("Librarian with username " + username + " already exists");
        }

        if (profileDTO.workTime() == null) {
            throw new IllegalArgumentException("working time cannot be null or empty");
        }


        Profile profile = new Profile();
        profile.setUsername(profileDTO.username());
        profile.setPassword(bCryptPasswordEncoder.encode(profileDTO.password()));
        profile.setRole(ProfileRole.ROLE_LIBRARIAN);
        profile.setWorkTime(profileDTO.workTime());
        profile.setCreatedDate(LocalDateTime.now());
        profile.setVisible(true);

        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    public List<ProfileDTO> getAllLibrarians() {

        List<Profile> librarians = profileRepository.findAllBy();

        return librarians.stream().map(ProfileMapper.INSTANCE::toDto).toList();


    }

    public ProfileDTO getById(Long librarianId) {

        Profile profile = getEntityById(librarianId);

        return ProfileMapper.INSTANCE.toDto(profile);


    }

    private Boolean isUserNameExist(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        return profileRepository.existsByUsername(userName);
    }

    private Profile getEntityById(Long librarianId) {
        if (librarianId == null) {
            throw new IllegalArgumentException("Librarian ID cannot be null");
        }

        return profileRepository.findById(librarianId)
                .orElseThrow(() -> new DataNotFoundException("Librarian not found with ID: " + librarianId));
    }


}
