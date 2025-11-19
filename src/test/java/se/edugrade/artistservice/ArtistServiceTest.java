package se.edugrade.artistservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.edugrade.artistservice.dto.ArtistRequestDTO;
import se.edugrade.artistservice.dto.ArtistResponseDTO;
import se.edugrade.artistservice.entities.Artist;
import se.edugrade.artistservice.exceptions.DuplicateArtistException;
import se.edugrade.artistservice.repositories.ArtistRepository;
import se.edugrade.artistservice.services.ArtistService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    ArtistRepository artistRepo;

    @InjectMocks
    ArtistService artistService;

    private static Artist artist(Long id, String name) {
        Artist a = new Artist();
        a.setId(id);
        a.setName(name);
        return a;
    }

    /****************Find All Genres****************/
    @Test
    void findAll_ReturnsAllArtists() throws Exception {
        when(artistRepo.findAll()).thenReturn(List.of(artist(1L, "Karol G"), artist(2L, "Bad Bunny")
        ));

        List<ArtistResponseDTO> result = artistService.findAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("Karol G", result.get(0).name());
        assertEquals(2L, result.get(1).id());
        assertEquals("Bad Bunny", result.get(1).name());
        verify(artistRepo).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_IfArtistNotFound() throws Exception {
        when(artistRepo.findAll()).thenReturn(List.of());
        List<ArtistResponseDTO> result = artistService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(artistRepo).findAll();
    }

    /****************Create****************/

    @Test
    void addArtist_throwsDuplicate_IfNameAlreadyExists() throws Exception {
        when(artistRepo.existsByNameIgnoreCase("Adele")).thenReturn(true);
        assertThrows(DuplicateArtistException.class,
                () -> artistService.addArtist(new ArtistRequestDTO("Adele")));
        verify(artistRepo, never()).save(any());
    }

    @Test
    void create_success_savesAndReturnsDTO() {
        var a = new ArtistRequestDTO("Natti Natasha");
        when(artistRepo.existsByNameIgnoreCase("Natti Natasha")).thenReturn(false);
        when(artistRepo.save(any(Artist.class))).thenAnswer(i -> {
            Artist artist = i.getArgument(0);
            artist.setId(1L);
            return artist;
        });

        ArtistResponseDTO dto = artistService.addArtist(a);
        assertEquals(1L, dto.id());
        assertEquals("Natti Natasha", dto.name());
        verify(artistRepo).existsByNameIgnoreCase("Natti Natasha");
        verify(artistRepo).save(argThat(arg -> "Natti Natasha".equals(arg.getName())));
    }

    @Test
    void create_blankName_throwsBadRequest() {
        var ar = new ArtistRequestDTO("  ");
        var ex = assertThrows(ResponseStatusException.class, () -> artistService.addArtist(ar));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verifyNoInteractions(artistRepo);
    }

    /****************Update****************/

    @Test
    void update_success_changeName() {
        var existingArtist = artist(4L, "Romeo Santos");
        when(artistRepo.findById(4L)).thenReturn(Optional.of(existingArtist));
        when(artistRepo.existsByNameIgnoreCase("Romeo")).thenReturn(false);
        when(artistRepo.save(any(Artist.class))).thenAnswer(inv -> inv.getArgument(0));


        var dto = artistService.updateArtist(4L, new ArtistRequestDTO("Romeo"));
        assertEquals(4L, dto.id());
        assertEquals("Romeo", dto.name());

        verify(artistRepo).findById(4L);
        verify(artistRepo).existsByNameIgnoreCase("Romeo");
        verify(artistRepo).save(argThat(a -> a.getId().equals(4L) && "Romeo".equals(a.getName())));
    }

    @Test
    void update_conflict_whenNameExists() {
        when(artistRepo.findById(8L)).thenReturn(Optional.of(artist(8L, "Merengue")));
        when(artistRepo.existsByNameIgnoreCase("Cumbia")).thenReturn(true);

        var ex = assertThrows(ResponseStatusException.class, ()-> artistService.updateArtist(8L, new ArtistRequestDTO("Cumbia")));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(artistRepo, never()).save(any());
    }


    /****************Remove****************/
    @Test
    void removeArtist() throws Exception {
        when(artistRepo.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> artistService.deleteArtist(1L));

        verify(artistRepo).existsById(1L);
        verify(artistRepo, never()).deleteById(anyLong());
        verify(artistRepo, never()).save(any());
    }

    /****************FindById & FindByName****************/

    @Test
    void create_and_findById_success() {
        var rq = new ArtistRequestDTO("Becky G");
        when(artistRepo.existsByNameIgnoreCase("Becky G")).thenReturn(false);
        when(artistRepo.save(any(Artist.class))).thenAnswer(inv -> {
            Artist a = inv.getArgument(0);
            a.setId(1L);
            return a;
        });

        when(artistRepo.findById(1L)).thenReturn(Optional.of(artist(1L, "Becky G")));
        ArtistResponseDTO created = artistService.addArtist(rq);

        ArtistResponseDTO found = artistService.findById(1L);

        assertEquals(1L, created.id());
        assertEquals("Becky G", created.name());

        assertEquals(1L, found.id());
        assertEquals("Becky G", found.name());

        verify(artistRepo).existsByNameIgnoreCase("Becky G");
        verify(artistRepo).save(argThat(g -> "Becky G".equals(g.getName())));
        verify(artistRepo).findById(1L);
    }

    @Test
    void create_and_findByName_success() {
        var rq = new ArtistRequestDTO("Don Omar");
        when(artistRepo.existsByNameIgnoreCase("Don Omar")).thenReturn(false);
        when(artistRepo.save(any(Artist.class))).thenAnswer(inv -> {
            Artist a = inv.getArgument(0);
            a.setId(2L);
            return a;
        });

        when(artistRepo.findByNameIgnoreCase("Don Omar")).thenReturn(Optional.of(artist(2L, "Don Omar")));

        ArtistResponseDTO created = artistService.addArtist(rq);
        ArtistResponseDTO found = artistService.findByName(" Don Omar ");

        assertEquals(2L, created.id());
        assertEquals("Don Omar", created.name());
        assertEquals(2L, found.id());
        assertEquals("Don Omar", found.name());
        verify(artistRepo).existsByNameIgnoreCase("Don Omar");
        verify(artistRepo).save(argThat(g -> "Don Omar".equals(g.getName())));
        verify(artistRepo).findByNameIgnoreCase("Don Omar");
    }

}
