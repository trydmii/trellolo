package ua.trydmi.trellolo.rest;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ua.trydmi.trellolo.dto.FileDto;
import ua.trydmi.trellolo.dto.ResetPasswordDto;
import ua.trydmi.trellolo.dto.UpdateUserDto;
import ua.trydmi.trellolo.dto.UserDto;
import ua.trydmi.trellolo.mapper.MyMapper;
import ua.trydmi.trellolo.model.User;
import ua.trydmi.trellolo.rest.exception.FileDownloadException;
import ua.trydmi.trellolo.service.FileService;
import ua.trydmi.trellolo.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {

    @Value("${s3.bucket-prefix}")
    private String BUCKET_PREFIX;

    private final UserService userService;
    private final MyMapper mapper;
    private final FileService fileService;
    private final AmazonS3 s3client;

    @GetMapping("/")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        UserDto userDto = mapper.userToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<UserDto> updateCurrentUser(Authentication authentication, @RequestBody @Valid UpdateUserDto updateUserDto) {
        User user = userService.findByUsername(authentication.getName());
        User updatedUser = userService.update(updateUserDto, user.getId());
        UserDto userDto = mapper.userToUserDto(updatedUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<HttpStatus> resetCurrentUserPassword(Authentication authentication, @Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        User user = userService.findByUsername(authentication.getName());
        userService.resetPassword(resetPasswordDto, user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileDto>> showAllFiles() {
        var allFiles = fileService.findAll().stream()
                .map(mapper::fileToFileDto)
                .toList();
        return new ResponseEntity<>(allFiles, HttpStatus.OK);
    }

    @GetMapping("/files/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id) {
        var file = fileService.findById(id);
        if (file == null) {
            throw new EntityNotFoundException("file not found");
        }
        String bucketName = BUCKET_PREFIX + file.getAuthor().getUsername();
        var s3object = s3client.getObject(bucketName, file.getName());
        var objectContent = s3object.getObjectContent();
        byte[] bytes;
        try {
            bytes = objectContent.readAllBytes();
        } catch (IOException e) {
            throw new FileDownloadException("file download error");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentLength(bytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(bytes));
    }

}
