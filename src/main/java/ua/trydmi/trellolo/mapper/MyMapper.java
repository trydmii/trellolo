package ua.trydmi.trellolo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.trydmi.trellolo.dto.AdminUserReadDto;
import ua.trydmi.trellolo.dto.FileDto;
import ua.trydmi.trellolo.dto.RegisterRequestDto;
import ua.trydmi.trellolo.dto.UserDto;
import ua.trydmi.trellolo.model.File;
import ua.trydmi.trellolo.model.User;

@Mapper(componentModel = "spring")
public interface MyMapper {

    @Mapping(target = "userStatus", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    User requestToUser(RegisterRequestDto requestDto);

    @Mapping(target = "userStatus", ignore = true)
    UserDto userToUserDto(User user);

    AdminUserReadDto userToAdminUserReadDto(User user);

    FileDto fileToFileDto(File file);

}
