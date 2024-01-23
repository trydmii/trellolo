package ua.trydmi.trellolo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.trydmi.trellolo.model.File;
import ua.trydmi.trellolo.model.User;
import ua.trydmi.trellolo.repository.FileRepository;
import ua.trydmi.trellolo.service.FileService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public List<File> findByAuthor(User user) {
        List<File> files = fileRepository.findByAuthor(user);
        log.info("IN FileServiceImpl.findByAuthor, found: {}", files);
        return files;
    }

    @Override
    public File save(File file) {
        File saved = fileRepository.save(file);
        log.info("IN FileServiceImpl.save, saved: {}", saved);
        return saved;
    }

    @Override
    public File findById(Long id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("File not found"));
        log.info("IN FileServiceImpl.findById, found file: {} by id: {}", file, id);
        return file;
    }

    @Override
    public void deleteById(Long id) {
        fileRepository.deleteById(id);
        log.info("IN FileServiceImpl.deleteById, deleted file with id: {}", id);
    }

    @Override
    public List<File> findAll() {
        List<File> files = fileRepository.findAll();
        log.info("IN FileServiceImpl.findAll, found: {}", files);
        return files;
    }

}
