package doston.code.service;


import doston.code.dto.JwtDTO;
import doston.code.dto.request.TokenRefreshRequestDTO;
import doston.code.dto.response.JwtResponseDTO;
import doston.code.dto.response.TokenRefreshResponseDTO;
import doston.code.exception.UnauthorizedException;
import doston.code.repository.LibrarianRepository;
import doston.code.security.CustomUserDetails;
import doston.code.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
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

    private final LibrarianRepository librarianRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public JwtResponseDTO login(String username, String password) {


        librarianRepository.findByUsernameAndVisibleTrue(username)
                .orElseThrow(() -> new UnauthorizedException("Login or password is wrong"));


        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                return new JwtResponseDTO(
                        jwtUtil.encode(username, userDetails.getRole().toString()),
                        "Bearer",
                        jwtUtil.refreshToken(username, userDetails.getRole().toString()),
                        username,
                        List.of(userDetails.getRole().toString())
                );
            }
            throw new UnauthorizedException("Login or password is wrong");
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Login or password is wrong");

        }
    }


    public TokenRefreshResponseDTO getNewAccessToken(TokenRefreshRequestDTO dto) {

        JwtUtil.TokenValidationResult validationResult = jwtUtil.validateToken(dto.refreshToken());

        if (!validationResult.valid()) {
            throw new UnauthorizedException(validationResult.message());
        }

        try {

            JwtDTO jwtDTO = jwtUtil.decode(dto.refreshToken());

            librarianRepository.findByUsernameAndVisibleTrue(jwtDTO.login())
                    .orElseThrow(() -> new UnauthorizedException("Account is no longer active"));

            String newAccessToken = jwtUtil.encode(jwtDTO.login(), jwtDTO.role());

            return new TokenRefreshResponseDTO(newAccessToken, dto.refreshToken());

        } catch (JwtException e) {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

}
