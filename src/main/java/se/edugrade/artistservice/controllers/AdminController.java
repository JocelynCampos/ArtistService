package se.edugrade.artistservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.edugrade.artistservice.dto.ArtistRequestDTO;
import se.edugrade.artistservice.dto.ArtistResponseDTO;
import se.edugrade.artistservice.services.ArtistService;


@RestController
@RequestMapping("/edufy/v1/artist")
public class AdminController {

    private final ArtistService artistService;

    public AdminController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/add")
    public ResponseEntity<ArtistResponseDTO> addArtist(@RequestBody ArtistRequestDTO req) {
        var created = artistService.addArtist(req);
        return ResponseEntity.ok(created);
    }

     @PutMapping("/update/{id}") //Uppdatera en specifik artist
    public ResponseEntity<ArtistResponseDTO> updateArtist(@PathVariable Long id, @RequestBody ArtistRequestDTO req) {
        ArtistResponseDTO updated = artistService.updateArtist(id, req);
        return ResponseEntity.ok(updated);
     }

     @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void>removeArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
     }

     @GetMapping("/by_id/{id}")
    public ResponseEntity<ArtistResponseDTO> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.findById(id));
     }

}


