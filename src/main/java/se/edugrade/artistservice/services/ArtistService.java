package se.edugrade.artistservice.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.edugrade.artistservice.dto.ArtistRequestDTO;
import se.edugrade.artistservice.dto.ArtistResponseDTO;
import se.edugrade.artistservice.entities.Artist;
import se.edugrade.artistservice.exceptions.DuplicateArtistException;
import se.edugrade.artistservice.repositories.ArtistRepository;

import java.util.List;

@Service
@Transactional
public class ArtistService implements ArtistServiceInterface {

    private final ArtistRepository artistRepository;
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ArtistResponseDTO> findAll() {
        return artistRepository.findAll().stream()
                .map(artist -> new ArtistResponseDTO(artist.getId(), artist.getArtistName()))
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
        if (artistRepository.existsByArtistName(name)) {
            throw new DuplicateArtistException("Artist with name " + name + " already exists");
        }
        var artist = new Artist();
        artist.setArtistName(name);
        artist = artistRepository.save(artist);
        return new ArtistResponseDTO(artist.getId(), artist.getArtistName());
    }

    @Override
    public ArtistResponseDTO updateArtist(Long id, ArtistRequestDTO rq) {
        return null;
    }

    @Override
    public void deleteArtist(Long id) {

    }




}
