package ua.trydmi.trellolo.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.trydmi.trellolo.dto.AdminUserReadDto;
import ua.trydmi.trellolo.mapper.MyMapper;
import ua.trydmi.trellolo.service.UserService;
import ua.trydmi.trellolo.utils.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminRestControllerV1Test {
    @Mock
    private UserService userService;
    @Mock
    private MyMapper mapper;

    @InjectMocks
    private AdminRestControllerV1 adminRestControllerV1;

    @Test
    void testShowAllUsers_WhenWithData_ThenShouldSucceed() {
        when(userService.findAll()).thenReturn(List.of(TestUtils.getUser()));
        ResponseEntity<List<AdminUserReadDto>> listResponseEntity = adminRestControllerV1.showAllUsers();
        assertNotNull(listResponseEntity);
    }

    @Test
    void testBanUser_WhenWithData_ThenShouldSucceed() {
        when(userService.findById(1L)).thenReturn(TestUtils.getUser());
        ResponseEntity<HttpStatus> banUser = adminRestControllerV1.banUser(1L);
        assertNotNull(banUser);
    }
}
