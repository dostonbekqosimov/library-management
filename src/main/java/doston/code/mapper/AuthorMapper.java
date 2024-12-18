package doston.code.mapper;

import doston.code.dto.request.AuthorRequestDTO;
import doston.code.dto.response.AuthorResponseDTO;
import doston.code.dto.response.GenreResponseDTO;
import doston.code.entity.Author;
import doston.code.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "updatedDate", target = "updatedDate")
    AuthorResponseDTO toDto(Author Author);


    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    Author toEntity(AuthorRequestDTO requestDTO);


}