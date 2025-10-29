package se.edugrade.artistservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "artists")
public class Artist {

    String id;


}
