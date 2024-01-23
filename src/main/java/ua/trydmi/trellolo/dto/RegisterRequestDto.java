package ua.trydmi.trellolo.dto;

public record RegisterRequestDto(
//        @NotNull(message = "Username is required")
//        @Pattern(regexp = "^[A-Za-z_]+$", message = "Username can contain only English alphabet letters and underscores")
//        @Max(value = 50, message = "Username must be less than 50 characters")
        String username,

//        @NotNull(message = "firstname is required")
//        @Pattern(regexp = "^[A-Za-z]+$",
//                message = "First name can contain only English alphabet letters")
//        @Max(value = 50, message = "firstname must be less than 50 characters")
        String firstname,

//        @NotNull(message = "lastname is required")
//        @Pattern(regexp = "^[A-Za-z]+$",
//                message = "First name can contain only English alphabet letters")
//        @Max(value = 50, message = "lastname must be less than 50 characters")
        String lastname,

//        @NotNull(message = "password is required")
//        @Max(value = 30, message = "password must be less than 30 characters")
        String password
) {
}