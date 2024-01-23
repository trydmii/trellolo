package ua.trydmi.trellolo.service;

import ua.trydmi.trellolo.model.File;
import ua.trydmi.trellolo.model.User;

import java.util.List;

public interface FileService {

    List<File> findByAuthor(User user);

    File save(File file);

    File findById(Long id);

    void deleteById(Long id);

    List<File> findAll();

}
