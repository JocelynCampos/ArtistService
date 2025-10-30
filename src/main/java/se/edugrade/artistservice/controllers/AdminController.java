package se.edugrade.artistservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.edugrade.artistservice.dto.ArtistRequestDTO;
import se.edugrade.artistservice.dto.ArtistResponseDTO;
import se.edugrade.artistservice.services.ArtistService;

@RestController
@RequestMapping("/edufy/v1/artist")
public class AdminController {

    private ArtistService artistService;

    public AdminController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/add")
    public ResponseEntity<ArtistResponseDTO> addArtist(@RequestBody ArtistRequestDTO artistName) {
        return ResponseEntity.ok(artistService.addArtist(artistName));
    }

    /**
     @PutMapping("/upd-artist/{id}") //Uppdatera en specifik artist

     @DeleteMapping("/rem-artist/{id}") //Ta bort en specifik artist

**/

}


