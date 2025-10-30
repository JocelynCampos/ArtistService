package se.edugrade.artistservice.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artists_name", length = 100, unique = true, nullable = false)
    private String artistName;


    public Artist() {}

    public Artist(Long id, String name) {
        this.id = id;
        this.artistName = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
