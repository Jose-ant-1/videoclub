package org.iesbelen.videoclub.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesbelen.videoclub.domain.Categoria;
import org.iesbelen.videoclub.domain.CategoriaDTO;
import org.iesbelen.videoclub.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
/*
    @GetMapping
    public ResponseEntity<List<Categoria>> all() {
        log.info("Accediendo a todas las categorias");
        List<Categoria> categorias = this.categoriaService.all();
        return ResponseEntity.ok(categorias);
    }
 */

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> all(
            @RequestParam("buscar") Optional<String> buscar,
            @RequestParam("ordenar") Optional<String> ordenar) {

        log.info("Accediendo a categorias con DTO y filtros");

        // Llamamos al nuevo método sin haber tocado el 'all()' original
        List<CategoriaDTO> listaDTO = this.categoriaService.findAllDTO(buscar, ordenar);

        return ResponseEntity.ok(listaDTO);
    }

    @PostMapping
    public ResponseEntity<Categoria> newCategoria(@Valid @RequestBody Categoria categoria) {
        log.info("Creando nueva categoria {}", categoria.getNombre());
        Categoria categoria1 = this.categoriaService.save(categoria);
        return new ResponseEntity<>(categoria1, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> one(@PathVariable("id") Long id) {
        Categoria categoria = this.categoriaService.one(id);
        return ResponseEntity.ok(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> replaceCategoria(@PathVariable("id") Long id, @Valid @RequestBody Categoria categoria) {
        log.info("reemplazando categoria con ID: {}",id);
        Categoria actualizado = this.categoriaService.replace(id, categoria);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCategoria(@PathVariable("id") Long id) {
        log.info("Eliminando categoria con ID: {}", id);
        this.categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
