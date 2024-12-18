package doston.code.mapper;

import doston.code.dto.response.ProfileDTO;
import doston.code.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileMapper {
    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "workTime", target = "workTime")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "visible", target = "visible")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "workTime", target = "workTime")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "visible", target = "visible")
    Profile toEntity(ProfileDTO profileDto);
}
