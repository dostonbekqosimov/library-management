package doston.code.mapper;

import doston.code.dto.request.BookRequestDTO;
import doston.code.dto.response.BookResponseDTO;
import doston.code.entity.Book;
import doston.code.entity.BookGenre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "authorId", target = "authorId")
    @Mapping(source = "bookGenres", target = "genreIdList", qualifiedByName = "toGenreIdList")
    @Mapping(source = "count", target = "count")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "updatedDate", target = "updatedDate")
    BookResponseDTO toDto(Book book);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "authorId", target = "authorId")
    @Mapping(source = "count", target = "count")
    Book toEntity(BookRequestDTO requestDTO);

    @Named("toGenreIdList")
    default List<Long> toGenreIdList(List<BookGenre> bookGenres) {
        if (bookGenres == null) {
            return null;
        }
        return bookGenres.stream()
                .map(BookGenre::getGenreId)
                .collect(Collectors.toList());
    }
}