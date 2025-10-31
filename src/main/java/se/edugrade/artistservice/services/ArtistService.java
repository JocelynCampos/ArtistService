package se.edugrade.artistservice.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import se.edugrade.artistservice.dto.ArtistRequestDTO;
import se.edugrade.artistservice.dto.ArtistResponseDTO;
import se.edugrade.artistservice.entities.Artist;
import se.edugrade.artistservice.exceptions.DuplicateArtistException;
import se.edugrade.artistservice.repositories.ArtistRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
public class ArtistService implements ArtistServiceInterface {

    private static final Logger logger = Logger.getLogger(ArtistService.class.getName());

    private final ArtistRepository artistRepository;
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ArtistResponseDTO> findAll() {
        return artistRepository.findAll().stream()
                .map(artist -> new ArtistResponseDTO(artist.getId(), artist.getName()))
                .toList();
    }

    @Override
    public ArtistResponseDTO findById(Long id) {
        return null;
    }

    @Override
    public ArtistResponseDTO addArtist(ArtistRequestDTO rq) {
        var name = rq.name().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (artistRepository.existsByName(name)) {
            throw new DuplicateArtistException("Artist with name " + name + " already exists");
        }
        var artist = new Artist();
        artist.setName(name);
        artist = artistRepository.save(artist);
        logger.info("Artist " + name + " added");
        return new ArtistResponseDTO(artist.getId(), artist.getName());
    }

    @Override
    public ArtistResponseDTO updateArtist(Long id, ArtistRequestDTO rq) {
        var artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found with id" + id));

        var raw = rq.name();
        if (raw == null || raw.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        }
        var name = raw.trim();

        if (!name.equals(artist.getName()) && artistRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Artist with name " + name + " already exist.");
        }

        artist.setName(name);
        var saved = artistRepository.save(artist);
        logger.info("Artist with id: " + id + " is updated to: " + name);
        return new ArtistResponseDTO(saved.getId(), saved.getName());
    }

    @Override
    public void deleteArtist(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found with id:" + id);
        }
        artistRepository.deleteById(id);
        logger.info("Artist with id: " + id + " was deleted.");
    }




}
