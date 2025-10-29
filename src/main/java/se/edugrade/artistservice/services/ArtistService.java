package se.edugrade.artistservice.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.edugrade.artistservice.dto.ArtistRequestDTO;
import se.edugrade.artistservice.dto.ArtistResponseDTO;
import se.edugrade.artistservice.entities.Artist;
import se.edugrade.artistservice.repositories.ArtistRepository;

@Service
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public ArtistResponseDTO addArtist(ArtistRequestDTO rq) {
        Artist artist = new Artist();
        artist.setArtistName(rq.name().trim());
        artist = artistRepository.save(artist);
        return new ArtistResponseDTO(artist.getId(), artist.getArtistName());
    }
}
