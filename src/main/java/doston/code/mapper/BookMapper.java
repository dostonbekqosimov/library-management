package doston.code.mapper;

import doston.code.dto.request.BookRequestDTO;
import doston.code.dto.response.BookResponseDTO;
import doston.code.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "authorId", target = "authorId")
    @Mapping(source = "genreId", target = "genreId")
    @Mapping(source = "count", target = "count")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "updatedDate", target = "updatedDate")
    BookResponseDTO toDto(Book book);


    @Mapping(source = "title", target = "title")
    @Mapping(source = "authorId", target = "authorId")
    @Mapping(source = "genreId", target = "genreId")
    @Mapping(source = "count", target = "count")
    Book toEntity(BookRequestDTO requestDTO);

}
