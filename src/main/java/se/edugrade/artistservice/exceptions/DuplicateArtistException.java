package se.edugrade.artistservice.exceptions;

public class DuplicateArtistException extends RuntimeException {
    public DuplicateArtistException(String message) {
        super(message);
    }
}
