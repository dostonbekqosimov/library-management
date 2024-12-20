package doston.code.controller;

import doston.code.dto.request.TokenRefreshRequestDTO;
import doston.code.dto.response.JwtResponseDTO;
import doston.code.dto.request.LoginDTO;
import doston.code.dto.response.TokenRefreshResponseDTO;
import doston.code.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API endpoints for user authentication")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(
            @RequestBody @Valid LoginDTO loginDTO
    ) {
        return ResponseEntity.ok(authService.login(loginDTO.login(), loginDTO.password()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponseDTO> refreshToken(@Valid @RequestBody TokenRefreshRequestDTO request
    ) {

        return ResponseEntity.ok(authService.getNewAccessToken(request));
    }
}
