package ua.trydmi.trellolo.rest;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.trydmi.trellolo.dto.FileDto;
import ua.trydmi.trellolo.exception.UserNotFoundException;
import ua.trydmi.trellolo.mapper.MyMapper;
import ua.trydmi.trellolo.model.File;
import ua.trydmi.trellolo.service.FileService;
import ua.trydmi.trellolo.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/moder")
public class ModerRestControllerV1 {

    @Value("${s3.bucket-prefix}")
    private String BUCKET_PREFIX;

    private final UserService userService;
    private final AmazonS3 s3client;
    private final FileService fileService;
    private final MyMapper mapper;

    @PostMapping("/files")
    public ResponseEntity<FileDto> uploadFile(@RequestParam("file") MultipartFile file,
                                        Authentication authentication) throws IOException {
        var name = file.getOriginalFilename();
        var author = userService.findByUsername(authentication.getName());

        String bucketName = BUCKET_PREFIX + author.getUsername();
        if (!s3client.doesBucketExistV2(bucketName)) {
            s3client.createBucket(bucketName);
        }

        List<File> userFiles = fileService.findByAuthor(author);
        for (File f : userFiles) {
            if (f.getName().equals(name)) {
                throw new FileAlreadyExistsException("File with name " + name + " already exists");
            }
        }

        s3client.putObject(bucketName, name, file.getInputStream(), new ObjectMetadata());
        File fileEntity = File.builder()
                .name(name)
                .author(author)
                .build();

        File savedEntity = fileService.save(fileEntity);

        FileDto fileDto = mapper.fileToFileDto(savedEntity);

        return new ResponseEntity<>(fileDto, CREATED);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<List<FileDto>> showFilesByUserId(@PathVariable Long id) {
        var author = userService.findById(id);
        if (author == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        var files = fileService.findByAuthor(author);

        List<FileDto> fileDtos = files.stream()
                .map(mapper::fileToFileDto)
                .toList();
        return new ResponseEntity<>(fileDtos, HttpStatus.OK);
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileDto>> showFilesForCurrentUser(Authentication authentication) {
        var author = userService.findByUsername(authentication.getName());
        var files = fileService.findByAuthor(author);

        List<FileDto> fileDtos = files.stream()
                .map(mapper::fileToFileDto)
                .toList();
        return new ResponseEntity<>(fileDtos, HttpStatus.OK);
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<HttpStatus> deleteFileById(@PathVariable Long id,
                                            Authentication authentication) {
        var file = fileService.findById(id);
        if (file == null) {
            throw new EntityNotFoundException("File with id " + id + " not found");
        }
        String bucketName = BUCKET_PREFIX + authentication.getName();
        s3client.deleteObject(bucketName, file.getName());
        fileService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
