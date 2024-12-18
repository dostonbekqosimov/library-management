package doston.code.controller;

import doston.code.dto.CreateLibrarianProfileDTO;
import doston.code.dto.ProfileDTO;
import doston.code.service.LibrarianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/librarians")
@RequiredArgsConstructor
public class LibrarianController {

    private final LibrarianService librarianService;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> createLibrarianProfile(
            @RequestBody @Valid CreateLibrarianProfileDTO profileDTO
    ) {
        ProfileDTO createdProfile = librarianService.createLibrarianProfile(profileDTO);
        return ResponseEntity.ok(createdProfile);
    }

   // bazi api lar qo'shamiz keyinroq(get all, get by id, update so on)

}
