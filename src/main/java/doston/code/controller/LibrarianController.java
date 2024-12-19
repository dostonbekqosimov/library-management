package doston.code.controller;

import doston.code.dto.request.PasswordUpdateDTO;
import doston.code.dto.request.ProfileRequestDTO;
import doston.code.dto.response.ProfileDTO;
import doston.code.service.LibrarianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/librarians")
@RequiredArgsConstructor
public class LibrarianController {

    private final LibrarianService librarianService;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> createLibrarianProfile(
            @RequestBody @Valid ProfileRequestDTO profileDTO
    ) {
        ProfileDTO createdProfile = librarianService.createLibrarianProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAllLibrarians() {
        return ResponseEntity.ok().body(librarianService.getAllLibrarians());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getLibrarianById(@PathVariable("id") Long librarianId) {

        return ResponseEntity.ok().body(librarianService.getById(librarianId));

    }

    @PatchMapping("/me/password")
    public ResponseEntity<String> changeOwnPassword(@RequestBody @Valid PasswordUpdateDTO request) {

        String response = librarianService.changeOwnPassword(request);
        return ResponseEntity.ok().body(response);
    }

    // shuni spring securitydan to'g'irlash kerak
    @PatchMapping("/admin/{id}/password")
    public ResponseEntity<String> resetPasswordByAdmin(@RequestBody @Valid PasswordUpdateDTO request,
                                                       @PathVariable("id") Long librarianId) {

        String response = librarianService.resetPasswordByAdmin(request, librarianId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteLibrarianById(@PathVariable("id") Long librarianId){

        librarianService.deleteLibrarianById(librarianId);
        return ResponseEntity.noContent().build();

    }


}
