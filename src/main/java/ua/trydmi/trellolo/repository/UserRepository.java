package ua.trydmi.trellolo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.trydmi.trellolo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);

}
