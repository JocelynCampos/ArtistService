package se.edugrade.artistservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.artistservice.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
