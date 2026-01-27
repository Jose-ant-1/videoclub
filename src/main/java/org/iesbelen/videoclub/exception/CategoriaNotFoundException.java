package org.iesbelen.videoclub.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Long id) {
        super("not found Categoria with id: " + id);
    }
}
