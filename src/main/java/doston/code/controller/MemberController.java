package doston.code.controller;

import doston.code.dto.request.MemberRequestDTO;
import doston.code.dto.response.MemberResponseDTO;
import doston.code.dto.response.MemberResponseDTO;
import doston.code.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDTO> addMember(@RequestBody @Valid MemberRequestDTO requestDTO) {

        MemberResponseDTO response = memberService.addMember(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {

        List<MemberResponseDTO> responseList = memberService.getAllMembers();

        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable("id") Long memberId) {

        MemberResponseDTO response = memberService.getMemberById(memberId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMemberById(@PathVariable("id") Long memberId,
                                                              @RequestBody @Valid MemberRequestDTO requestDTO) {

        MemberResponseDTO response = memberService.updateMemberById(memberId, requestDTO);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable("id") Long memberId) {

        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }
}
