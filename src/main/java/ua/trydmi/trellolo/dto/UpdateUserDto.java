package ua.trydmi.trellolo.dto;

public record UpdateUserDto(
//        @NotNull(message = "Username is required")
//        @Pattern(regexp = "^[A-Za-z_]+$", message = "Username can contain only English alphabet letters and underscores")
//        @Min(value = 3, message = "Username must be at least 3 characters")
//        @Max(value = 50, message = "Username must be less than 50 characters")
        String username,

//        @NotNull(message = "firstname is required")
//        @Pattern(regexp = "^[A-Za-z]+$",
//                message = "First name can contain only English alphabet letters")
//        @Min(value = 3, message = "firstname must be at least 3 characters")
//        @Max(value = 50, message = "firstname must be less than 50 characters")
        String firstname,

//        @NotNull(message = "lastname is required")
//        @Pattern(regexp = "^[A-Za-z]+$",
//                message = "Last name can contain only English alphabet letters")
//        @Min(value = 3, message = "lastname must be at least 3 characters")
//        @Max(value = 50, message = "lastname must be less than 50 characters")
        String lastname
) {
}
