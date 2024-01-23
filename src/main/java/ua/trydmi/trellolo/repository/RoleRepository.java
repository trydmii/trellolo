package ua.trydmi.trellolo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.trydmi.trellolo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
