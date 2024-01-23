package ua.trydmi.trellolo.service;

import ua.trydmi.trellolo.dto.ResetPasswordDto;
import ua.trydmi.trellolo.dto.UpdateUserDto;
import ua.trydmi.trellolo.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    User findByUsername(String name);

    User findById(Long id);

    User update(UpdateUserDto updateUserDto, Long id);

    void resetPassword(ResetPasswordDto resetPasswordDto, Long id);

    List<User> findAll();

}
