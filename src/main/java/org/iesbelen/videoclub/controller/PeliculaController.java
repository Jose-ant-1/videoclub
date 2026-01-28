package org.iesbelen.videoclub.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.iesbelen.videoclub.domain.Pelicula;
import org.iesbelen.videoclub.service.PeliculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> all(@RequestParam(value = "orden", required = false) String[] orden,
                                              @RequestParam(value = "paginado", required = false) String[] paginado) {

        log.info("Accediendo a películas con orden: {} y paginado: {}", Arrays.toString(orden), Arrays.toString(paginado));
        Map<String, Object> resultado = this.peliculaService.all(orden,paginado);
        //List<Pelicula> lista = this.peliculaService.all();
        return ResponseEntity.ok(resultado);
    }



    @PostMapping
    public ResponseEntity<Pelicula> newPelicula(@Valid @RequestBody Pelicula pelicula) {
        log.info("Creando nueva película: {}", pelicula.getTitulo());
        Pelicula peliculaGuardada = this.peliculaService.save(pelicula);

        return new ResponseEntity<>(peliculaGuardada, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> one(@PathVariable("id") Long id) {
        // El servicio debería lanzar una excepción si no existe, capturada por un GlobalExceptionHandler
        Pelicula pelicula = this.peliculaService.one(id);
        return ResponseEntity.ok(pelicula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> replacePelicula(@PathVariable("id") Long id,
                                                    @Valid @RequestBody Pelicula pelicula) {
        log.info("Actualizando película con ID: {}", id);
        Pelicula actualizada = this.peliculaService.replace(id, pelicula);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePelicula(@PathVariable("id") Long id) {
        log.info("Eliminando película con ID: {}", id);
        this.peliculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
