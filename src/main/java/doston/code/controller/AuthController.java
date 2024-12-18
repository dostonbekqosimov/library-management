package doston.code.controller;

import doston.code.dto.CreateLibrarianProfileDTO;
import doston.code.dto.JwtResponseDTO;
import doston.code.dto.LoginDTO;
import doston.code.dto.ProfileDTO;
import doston.code.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(
            @RequestBody @Valid LoginDTO loginDTO
    ) {
        return ResponseEntity.ok(authService.login(loginDTO.login(), loginDTO.password()));
    }





}
