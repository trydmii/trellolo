package ua.trydmi.trellolo.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.trydmi.trellolo.dto.AdminUserReadDto;
import ua.trydmi.trellolo.mapper.MyMapper;
import ua.trydmi.trellolo.model.User;
import ua.trydmi.trellolo.model.UserStatus;
import ua.trydmi.trellolo.service.UserService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminRestControllerV1 {

    @Value("${s3.bucket-prefix}")
    private String BUCKET_PREFIX;

    private final UserService userService;
    private final MyMapper mapper;

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserReadDto>> showAllUsers() {
        var users = userService.findAll().stream()
                .map(mapper::userToAdminUserReadDto)
                .toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/ban/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> banUser(@PathVariable Long id) {
        User user = userService.findById(id);
        user.setUserStatus(UserStatus.BANNED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
