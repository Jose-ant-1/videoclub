package org.iesbelen.videoclub.exception;

public class IdiomaNotFoundException extends RuntimeException {
    public IdiomaNotFoundException(Long id) {
        super("not found Idioma with id: " + id);
    }
}
