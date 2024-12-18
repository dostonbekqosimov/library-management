package doston.code.service;


import doston.code.dto.response.JwtResponseDTO;
import doston.code.exception.UnauthorizedException;
import doston.code.repository.ProfileRepository;
import doston.code.security.CustomUserDetails;
import doston.code.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;


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


}
