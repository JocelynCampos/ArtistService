package se.edugrade.artistservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.artistservice.entities.Artist;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Artist> findByName(String name);
}
