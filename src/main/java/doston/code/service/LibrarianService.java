package doston.code.service;

import doston.code.dto.CreateLibrarianProfileDTO;
import doston.code.dto.ProfileDTO;
import doston.code.entity.Profile;
import doston.code.enums.ProfileRole;
import doston.code.mapper.ProfileMapper;
import doston.code.repository.ProfileRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LibrarianService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRepository profileRepository;



    public ProfileDTO createLibrarianProfile(@Valid CreateLibrarianProfileDTO profileDTO) {
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
}
