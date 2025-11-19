package se.edugrade.artistservice.services;

import se.edugrade.artistservice.dto.ArtistRequestDTO;
import se.edugrade.artistservice.dto.ArtistResponseDTO;
import se.edugrade.artistservice.entities.Artist;

import java.util.List;

public interface ArtistServiceInterface {

    List<ArtistResponseDTO> findAll();
    ArtistResponseDTO findById(Long id);
    ArtistResponseDTO findByName(String name);

    ArtistResponseDTO addArtist(ArtistRequestDTO rq);
    ArtistResponseDTO updateArtist(Long id, ArtistRequestDTO rq);
    void deleteArtist(Long id);
}
