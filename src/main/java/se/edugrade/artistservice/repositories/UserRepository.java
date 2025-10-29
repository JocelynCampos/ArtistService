package se.edugrade.artistservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.artistservice.entities.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
}
