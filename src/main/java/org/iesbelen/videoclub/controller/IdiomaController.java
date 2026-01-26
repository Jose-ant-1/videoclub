package org.iesbelen.videoclub.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesbelen.videoclub.domain.Idioma;
import org.iesbelen.videoclub.domain.Pelicula;
import org.iesbelen.videoclub.service.IdiomaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/idiomas")
public class IdiomaController {

    private final IdiomaService idiomaService;

    public IdiomaController(IdiomaService idiomaService) { this.idiomaService = idiomaService; }

    @GetMapping
    public ResponseEntity<List<Idioma>> all() {
        log.info("Accediendo a todos los idiomas");
        List<Idioma> lista = this.idiomaService.all();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<Idioma> newIdioma(@Valid @RequestBody Idioma idioma) {
        log.info("Creando nuevo idioma: {}", idioma.getNombre());
        Idioma idiomaGuardado = this.idiomaService.save(idioma);
        return new ResponseEntity<>(idiomaGuardado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idioma> one(@PathVariable("id") Long id) {
        Idioma idioma = this.idiomaService.one(id);
        return ResponseEntity.ok(idioma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idioma> replaceIdioma(@PathVariable("id") Long id, @Valid @RequestBody Idioma idioma) {
        log.info("Actualizando idioma con ID: {}", id);
        Idioma actualizado = this.idiomaService.replace(id, idioma);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteIdioma(@PathVariable("id") Long id) {
        log.info("Eliminando idioma con ID: {}", id);
        this.idiomaService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
