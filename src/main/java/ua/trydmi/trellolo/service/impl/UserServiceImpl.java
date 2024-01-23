package ua.trydmi.trellolo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.trydmi.trellolo.dto.ResetPasswordDto;
import ua.trydmi.trellolo.dto.UpdateUserDto;
import ua.trydmi.trellolo.exception.UserNotFoundException;
import ua.trydmi.trellolo.model.Role;
import ua.trydmi.trellolo.model.User;
import ua.trydmi.trellolo.model.UserStatus;
import ua.trydmi.trellolo.repository.RoleRepository;
import ua.trydmi.trellolo.repository.UserRepository;
import ua.trydmi.trellolo.service.UserService;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String USER_NOT_FOUND_MESSAGE = "user not found";

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleUser));
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setUserStatus(UserStatus.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN UserServiceImpl.register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public User findByUsername(String name) {
        User result = userRepository.findByUsername(name);
        if (result != null) {
            log.info("IN UserServiceImpl.findByUsername - user: {} found by username {}", result, name);
            return result;
        }
        log.warn("IN UserServiceImpl.findByUsername user not found by username {}", name);
        throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
    }

    @Override
    public User update(UpdateUserDto updateUserDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        log.info("IN UserServiceImpl.update - user: {} found by id {}", user, id);

        user.setUsername(updateUserDto.username());
        user.setFirstName(updateUserDto.firstname());
        user.setLastName(updateUserDto.lastname());
        user.setUpdated(new Date());

        log.info("IN UserServiceImpl.update - user: {} updated", user);

        return userRepository.save(user);
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        log.info("IN UserServiceImpl.resetPassword - user found by id {}", id);

        user.setPassword(passwordEncoder.encode(resetPasswordDto.password()));
        user.setUpdated(new Date());
        userRepository.save(user);
        log.info("IN UserServiceImpl.resetPassword - user: {} password reset", user);
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        log.info("IN UserServiceImpl.findById - user {} found by id {}", user, id);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        log.info("IN UserServiceImpl.findAll - {} users found", users.size());
        return users;
    }

}
