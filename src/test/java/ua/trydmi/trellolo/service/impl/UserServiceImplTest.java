package ua.trydmi.trellolo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.trydmi.trellolo.model.Role;
import ua.trydmi.trellolo.model.User;
import ua.trydmi.trellolo.repository.RoleRepository;
import ua.trydmi.trellolo.repository.UserRepository;
import ua.trydmi.trellolo.utils.TestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegister_WhenWithData_ThenShouldSucceed() {
        when(roleRepository.findByName(any())).thenReturn(new Role());
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any())).thenReturn(TestUtils.getUser());

        User register = userService.register(TestUtils.getUser());
        verify(userRepository).save(any());
    }

    @Test
    void testFindByUsername_WhenWithData_ThenShouldSucceed() {
        when(userRepository.findByUsername(any())).thenReturn(TestUtils.getUser());
        User byUsername = userService.findByUsername("name");
        verify(userRepository).findByUsername(any());
    }

    @Test
    void testUpdate_WhenWithData_ThenShouldSucceed() {
        when(userRepository.findById(any())).thenReturn(Optional.of(TestUtils.getUser()));
        when(userRepository.save(any())).thenReturn(TestUtils.getUser());
        User update = userService.update(TestUtils.getUpdateUserDto(), 1L);
        assertNotNull(update);
        verify(userRepository).save(any());
    }

    @Test
    void testResetPassword_WhenWithData_ThenShouldSucceed() {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(TestUtils.getUser()));
        when(userRepository.save(any())).thenReturn(TestUtils.getUser());
        userService.resetPassword(TestUtils.getResetPasswordDto(), 1L);
        verify(userRepository).save(any());
    }

    @Test
    void testFindById_WhenWithData_ThenShouldSucceed() {
        when(userRepository.findById(any())).thenReturn(Optional.of(TestUtils.getUser()));
        User byId = userService.findById(1L);
        assertNotNull(byId);
        verify(userRepository).findById(any());
    }

    @Test
    void testFindAll_WhenWithData_ThenShouldSucceed() {
        when(userRepository.findAll()).thenReturn(List.of(TestUtils.getUser()));
        userService.findAll();
        verify(userRepository).findAll();
    }
}
