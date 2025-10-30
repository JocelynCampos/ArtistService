package se.edugrade.artistservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.edugrade.artistservice.dto.ArtistResponseDTO;
import se.edugrade.artistservice.services.ArtistService;

import java.util.List;

@RestController
@RequestMapping("/edufy/v1/artist")
public class CommonController {

    private final ArtistService artistService;

    public CommonController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/all")
    public List<ArtistResponseDTO> listAllArtists() {
        return artistService.findAll();
    }
}
