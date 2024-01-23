package ua.trydmi.trellolo.security.jwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.trydmi.trellolo.utils.TestUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {
    @Mock
    private JwtUtility jwtUtility;
    @Mock
    private JwtUserService userService;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @Test
    void testDoFilterInternal_WhenWithData_ThenShouldSucceed() throws Exception {
        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer ");
        when(jwtUtility.getUsernameFromToken("")).thenReturn("name");
        when(userService.loadUserByUsername("name")).thenReturn(TestUtils.getUserDetails());
        when(jwtUtility.validateToken("", TestUtils.getUserDetails())).thenReturn(true);
        jwtFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

}
