package doston.code.service;

import doston.code.dto.CreateLibrarianProfileDTO;
import doston.code.dto.ProfileDTO;
import doston.code.entity.Profile;
import doston.code.enums.ProfileRole;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.mapper.ProfileMapper;
import doston.code.repository.ProfileRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibrarianService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRepository profileRepository;



    public ProfileDTO createLibrarianProfile(@Valid CreateLibrarianProfileDTO profileDTO) {

        String username = profileDTO.username();
      if ( isUserNameExist(username)){
          throw new DataExistsException("Librarian with username " + username + " already exists");
      }

        Profile profile = new Profile();
        profile.setUsername(profileDTO.username());
        profile.setPassword(bCryptPasswordEncoder.encode(profileDTO.password()));
        profile.setRole(ProfileRole.ROLE_LIBRARIAN);
        profile.setWorkTime(profileDTO.workTime());
        profile.setCreatedDate(LocalDateTime.now());
        profile.setVisible(true);
        Profile savedProfile = profileRepository.save(profile);
        return ProfileMapper.INSTANCE.toDto(savedProfile);
    }

    public List<ProfileDTO> getAllLibrarians() {

        List<Profile> librarians = profileRepository.findAllBy();

        return librarians.stream().map(ProfileMapper.INSTANCE::toDto).toList();


    }

    private Boolean isUserNameExist(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return profileRepository.existsByUsername(userName);
    }

}
