package ua.trydmi.trellolo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record AdminUserReadDto(
        Long id,
        String username,
        String firstName,
        String lastName,
        String userStatus,
        Date created,
        Date updated,
        List<RoleDto> roles
) {
}
