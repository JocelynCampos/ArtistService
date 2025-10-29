package se.edugrade.artistservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.artistservice.entities.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
