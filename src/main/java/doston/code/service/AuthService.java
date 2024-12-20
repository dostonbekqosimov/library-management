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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final LibrarianRepository librarianRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public JwtResponseDTO login(String username, String password) {
        log.info("Login attempt for user: {}", username);

        librarianRepository.findByUsernameAndVisibleTrue(username)
                .orElseThrow(() -> {
                    log.warn("Login failed - user not found or not visible: {}", username);
                    return new UnauthorizedException("Login or password is wrong");
                });

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                log.info("User successfully authenticated: {}", username);

                return new JwtResponseDTO(
                        jwtUtil.encode(username, userDetails.getRole().toString()),
                        "Bearer",
                        jwtUtil.refreshToken(username, userDetails.getRole().toString()),
                        username,
                        List.of(userDetails.getRole().toString())
                );
            }
            log.warn("Authentication failed for user: {}", username);
            throw new UnauthorizedException("Login or password is wrong");
        } catch (BadCredentialsException e) {
            log.warn("Bad credentials provided for user: {}", username);
            throw new UnauthorizedException("Login or password is wrong");

        }
    }


    public TokenRefreshResponseDTO getNewAccessToken(TokenRefreshRequestDTO dto) {

        log.debug("Token refresh requested");

        JwtUtil.TokenValidationResult validationResult = jwtUtil.validateToken(dto.refreshToken());

        if (!validationResult.valid()) {
            log.warn("Invalid refresh token: {}", validationResult.message());
            throw new UnauthorizedException(validationResult.message());
        }

        try {

            JwtDTO jwtDTO = jwtUtil.decode(dto.refreshToken());
            log.debug("Refresh token decoded for user: {}", jwtDTO.login());

            librarianRepository.findByUsernameAndVisibleTrue(jwtDTO.login())
                    .orElseThrow(() -> {
                        log.warn("Token refresh failed - account no longer active: {}", jwtDTO.login());
                        return new UnauthorizedException("Account is no longer active");
                    });

            String newAccessToken = jwtUtil.encode(jwtDTO.login(), jwtDTO.role());
            log.info("New access token generated for user: {}", jwtDTO.login());

            return new TokenRefreshResponseDTO(newAccessToken, dto.refreshToken());

        } catch (JwtException e) {
            log.warn("JWT processing error during token refresh: {}", e.getMessage());
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

}
