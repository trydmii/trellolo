package ua.trydmi.trellolo.security.jwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.trydmi.trellolo.service.UserService;
import ua.trydmi.trellolo.utils.TestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserServiceTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private JwtUserService jwtUserService;

    @Test
    void testLoadByUsername_WhenWithData_ThenShouldSucceed() {
        when(userService.findByUsername("username")).thenReturn(TestUtils.getUser());
        UserDetails userDetails = jwtUserService.loadUserByUsername("username");
        assertNotNull(userDetails);
        verify(userService).findByUsername("username");
    }

    @Test
    void testLoadByUsername_WhenUserNull_ThenShouldThrow() {
        when(userService.findByUsername("username")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,
                () -> jwtUserService.loadUserByUsername("username"));
    }
}
