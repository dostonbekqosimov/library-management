package doston.code.mapper;

import doston.code.dto.request.ProfileRequestDTO;
import doston.code.dto.response.ProfileDTO;
import doston.code.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "workTime", target = "workTime")
    @Mapping(source = "createdDate", target = "createdDate")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "workTime", target = "workTime")
    Profile toEntity(ProfileRequestDTO requestDTO);
}
