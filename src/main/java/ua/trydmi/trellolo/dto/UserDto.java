package ua.trydmi.trellolo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record UserDto(
        String username,
        String firstName,
        String lastName,
        String userStatus,
        List<RoleDto> roles
) {
}
