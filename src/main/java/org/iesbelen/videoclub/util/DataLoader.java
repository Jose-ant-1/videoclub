package org.iesbelen.videoclub.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesbelen.videoclub.domain.Categoria;
import org.iesbelen.videoclub.domain.Idioma;
import org.iesbelen.videoclub.domain.Pelicula;
import org.iesbelen.videoclub.repository.CategoriaRepository;
import org.iesbelen.videoclub.repository.IdiomaRepository;
import org.iesbelen.videoclub.repository.PeliculaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor // Genera el constructor para inyectar los repositorios
public class DataLoader implements CommandLineRunner {

    private final PeliculaRepository peliculaRepository;
    private final IdiomaRepository idiomaRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional // Mantenemos la sesión abierta para las relaciones
    public void run(String... args) throws Exception {
        log.info("Cargando datos de prueba...");

        // 1. Crear e insertar Idiomas
        Idioma español = Idioma.builder().nombre("Español").build();
        Idioma ingles = Idioma.builder().nombre("Inglés").build();
        idiomaRepository.save(español);
        idiomaRepository.save(ingles);

        // 2. Crear e insertar Categorías
        Categoria accion = Categoria.builder().nombre("Acción").build();
        Categoria cienciaFiccion = Categoria.builder().nombre("Ciencia Ficción").build();
        categoriaRepository.save(accion);
        categoriaRepository.save(cienciaFiccion);

        // 3. Crear Película y asociar relaciones
        Pelicula p1 = Pelicula.builder()
                .titulo("Inception")
                .descripcion("Un ladrón que roba secretos a través de los sueños.")
                .precio(new BigDecimal("4.99"))
                .idioma(español)         // ManyToOne
                .categorias(Set.of(accion, cienciaFiccion)) // ManyToMany
                .build();

        peliculaRepository.save(p1);

        log.info("Datos cargados: Película '{}' guardada con ID {}", p1.getTitulo(), p1.getId());
    }
}
