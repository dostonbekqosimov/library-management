package doston.code.service;


import doston.code.dto.CreateLibrarianProfileDTO;
import doston.code.dto.JwtResponseDTO;
import doston.code.dto.ProfileDTO;
import doston.code.entity.Profile;
import doston.code.enums.ProfileRole;
import doston.code.exception.UnauthorizedException;
import doston.code.repository.ProfileRepository;
import doston.code.security.CustomUserDetails;
import doston.code.util.JwtUtil;
import doston.code.mapper.ProfileMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public JwtResponseDTO login(String username, String password) {


        profileRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Login or password is wrong"));


        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                return new JwtResponseDTO(
                        JwtUtil.encode(username, userDetails.getRole().toString()),
                        "Bearer",
                        JwtUtil.refreshToken(username, userDetails.getRole().toString()),
                        username,
                        List.of(userDetails.getRole().toString())
                );
            }
            throw new UnauthorizedException("Login or password is wrong");
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Login or password is wrong");

        }
    }

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
