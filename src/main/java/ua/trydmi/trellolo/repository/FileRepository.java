package ua.trydmi.trellolo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.trydmi.trellolo.model.File;
import ua.trydmi.trellolo.model.User;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByAuthor(User author);

}
