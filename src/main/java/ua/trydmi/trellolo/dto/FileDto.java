package ua.trydmi.trellolo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record FileDto(
        Long id,
        String name,
        UserDto author
) {
}
