package se.edugrade.artistservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.edugrade.artistservice.dto.ArtistRequestDTO;
import se.edugrade.artistservice.exceptions.DuplicateArtistException;
import se.edugrade.artistservice.repositories.ArtistRepository;
import se.edugrade.artistservice.services.ArtistService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    ArtistRepository repo;

    @InjectMocks
    ArtistService service;

    @Test
    void addArtist_throwsDuplicate_IfNameAlreadyExists() throws Exception {
        when(repo.existsByName("Adele")).thenReturn(true);
        assertThrows(DuplicateArtistException.class, () -> service.addArtist(new ArtistRequestDTO("Adele")));
        verify(repo, never()).save(any());

    }


}
