package ua.trydmi.trellolo.utils;

import org.springframework.security.core.userdetails.UserDetails;
import ua.trydmi.trellolo.dto.AdminUserReadDto;
import ua.trydmi.trellolo.dto.ResetPasswordDto;
import ua.trydmi.trellolo.dto.UpdateUserDto;
import ua.trydmi.trellolo.model.File;
import ua.trydmi.trellolo.model.User;
import ua.trydmi.trellolo.model.UserStatus;

import java.util.Collections;
import java.util.Date;

public class TestUtils {
    public static User getUser() {
        return User.builder()
                .firstName("firstName")
                .lastName("lastName")
                .password("password")
                .username("username")
                .created(new Date())
                .roles(Collections.emptyList())
                .updated(new Date())
                .userStatus(UserStatus.ACTIVE)
                .build();
    }

    public static File getFile() {
        return File.builder()
                .author(getUser())
                .name("name")
                .build();
    }

    public static UpdateUserDto getUpdateUserDto() {
        return new UpdateUserDto("username",
                "firstname",
                "lastname");
    }

    public static ResetPasswordDto getResetPasswordDto() {
        return new ResetPasswordDto("password");
    }

    public static UserDetails getUserDetails() {
        return org.springframework.security.core.userdetails.User.builder()
                .username("username")
                .password("password")
                .authorities(Collections.emptyList())
                .build();
    }

    public static AdminUserReadDto getAdminUserReadDto() {
        return new AdminUserReadDto(1L, "username", "firstname",
                "lastname", UserStatus.ACTIVE.name(), new Date(), new Date(),
                Collections.emptyList());
    }
}
