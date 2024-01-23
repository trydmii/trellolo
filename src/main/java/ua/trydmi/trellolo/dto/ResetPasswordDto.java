package ua.trydmi.trellolo.dto;

public record ResetPasswordDto(
//        @NotNull(message = "password is required")
//        @Max(value = 30, message = "password must be less than 30 characters")
        String password
) {
}
