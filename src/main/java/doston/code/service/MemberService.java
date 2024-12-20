package doston.code.service;

import doston.code.dto.request.MemberRequestDTO;
import doston.code.dto.response.MemberResponseDTO;
import doston.code.entity.Member;
import doston.code.exception.DataExistsException;
import doston.code.exception.DataNotFoundException;
import doston.code.exception.ForbiddenException;
import doston.code.mapper.MemberMapper;
import doston.code.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static doston.code.util.SpringSecurityUtil.getCurrentUserId;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberResponseDTO addMember(MemberRequestDTO requestDTO) {

        validateRequestDataForCreate(requestDTO);

        Long currentUserId = getCurrentUserId();

        Member newMember = memberMapper.toEntity(requestDTO);
        newMember.setCreatedById(currentUserId);
        newMember.setCreatedDate(LocalDateTime.now());
        newMember.setVisible(Boolean.TRUE);

        memberRepository.save(newMember);

        return memberMapper.toDto(newMember);


    }

    public List<MemberResponseDTO> getAllMembers() {

        List<Member> members = memberRepository.findAllByVisibleTrue();

        if (members.isEmpty()) {
            throw new DataNotFoundException("No member added yet");
        }

        return members.stream().map(memberMapper::toDto).toList();

    }

    public MemberResponseDTO getMemberById(Long memberId) {

        Member member = getEntityById(memberId);

        return memberMapper.toDto(member);

    }


    public MemberResponseDTO updateMemberById(Long memberId, MemberRequestDTO requestDTO) {

        Long currentUserId = getCurrentUserId();

        Member existingMember = getEntityById(memberId);

        validateLibrarianOwnershipForMember(currentUserId, existingMember.getCreatedById());

        validateRequestDataForUpdate(existingMember, requestDTO);

        existingMember.setName(requestDTO.name());
        existingMember.setPhone(requestDTO.phone());
        existingMember.setEmail(requestDTO.email());
        existingMember.setMembershipDate(requestDTO.membershipDate());
        existingMember.setUpdatedDate(LocalDateTime.now());

        memberRepository.save(existingMember);

        return memberMapper.toDto(existingMember);


    }

    public void deleteMemberById(Long memberId) {
        Long currentUserId = getCurrentUserId();

        // shu yerda faqat create_by_id ni olib kelsaham bo'ladi, instead of full
        Member existingMember = getEntityById(memberId);

        validateLibrarianOwnershipForMember(currentUserId, existingMember.getCreatedById());

        memberRepository.changeVisibility(memberId);

    }

    private Member getEntityById(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("Member ID cannot be null");
        }

        return memberRepository.findByIdAndVisibleTrue(memberId)
                .orElseThrow(() -> new DataNotFoundException("Member not found with ID: " + memberId));
    }

    private void validateLibrarianOwnershipForMember(Long librarianId, Long createdById) {

        if (!librarianId.equals(createdById)) {
            throw new ForbiddenException("Unauthorized action: Only the librarian who added this member can update their details.");
        }
    }


    private void validateRequestDataForCreate(MemberRequestDTO request) {


        if (memberRepository.existsByEmailAndVisibleTrue((request.email()))) {
            throw new DataExistsException("Email is already in use");
        }
        if (memberRepository.existsByPhoneAndVisibleTrue((request.phone()))) {
            throw new DataExistsException("Phone number is already in use");
        }


    }

    private void validateRequestDataForUpdate(Member existingMember, MemberRequestDTO requestDTO) {

        if (!existingMember.getEmail().equals(requestDTO.email())) {
            if (memberRepository.existsByEmailAndVisibleTrue(requestDTO.email())) {
                throw new DataExistsException("Email is already in use");
            }
        }

        if (!existingMember.getPhone().equals(requestDTO.phone())) {
            if (memberRepository.existsByPhoneAndVisibleTrue(requestDTO.phone())) {
                throw new DataExistsException("Phone number is already in use");
            }
        }
    }


}
