package doston.code.mapper;

import doston.code.dto.request.LibrarianRequestDTO;
import doston.code.dto.response.LibrarianDTO;
import doston.code.entity.Librarian;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LibrarianMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "workTime", target = "workTime")
    @Mapping(source = "createdDate", target = "createdDate")
    LibrarianDTO toDto(Librarian librarian);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "workTime", target = "workTime")
    Librarian toEntity(LibrarianRequestDTO requestDTO);
}
