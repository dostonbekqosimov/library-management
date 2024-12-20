package doston.code.mapper;

import doston.code.dto.request.MemberRequestDTO;
import doston.code.dto.response.LibrarianDTO;
import doston.code.dto.response.MemberResponseDTO;
import doston.code.entity.Librarian;
import doston.code.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "membershipDate", target = "membershipDate")
    @Mapping(source = "createdById", target = "createdById")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "updatedDate", target = "updatedDate")
    MemberResponseDTO toDto(Member member);


    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "membershipDate", target = "membershipDate")
    Member toEntity(MemberRequestDTO requestDTO);

}
