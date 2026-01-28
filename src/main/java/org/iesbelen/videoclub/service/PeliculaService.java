package org.iesbelen.videoclub.service;

import org.iesbelen.videoclub.domain.Pelicula;
import org.iesbelen.videoclub.exception.PeliculaNotFoundException;
import org.iesbelen.videoclub.repository.PeliculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public Map<String,Object> all(String[] orden, String[] paginado) {

        Sort s = Sort.unsorted();
        if (orden != null && orden.length == 2) {
            String columna = orden[0];
            String sentido = orden[1];
            s = sentido.equalsIgnoreCase("desc")
                    ? Sort.by(columna).descending()
                    : Sort.by(columna).ascending();
        }

        // 2. Lógica de Paginación (Valores por defecto: pag 0, tamaño 10)
        int pagina = 0;
        int tamanio = 10;
        if (paginado != null && paginado.length == 2) {
            try {
                pagina = Integer.parseInt(paginado[0]);
                tamanio = Integer.parseInt(paginado[1]);
            } catch (NumberFormatException e) {
                //log.error("Error convirtiendo parámetros de paginación", e);
            }
        }

        // 3. Combinar todo en un objeto Pageable
        Pageable p = PageRequest.of(pagina, tamanio, s);

        // 4. Consultar al repositorio
        Page<Pelicula> pageResult = this.peliculaRepository.findAll(p);

        // 5. Construir respuesta con metadatos
        Map<String, Object> response = new HashMap<>();
        response.put("peliculas", pageResult.getContent());
        response.put("totalElementos", pageResult.getTotalElements());
        response.put("totalPaginas", pageResult.getTotalPages());
        response.put("paginaActual", pageResult.getNumber());

        return response;
    }

    public Pelicula save(Pelicula pelicula) {
        return this.peliculaRepository.save(pelicula);
    }

    public Pelicula one(Long id) {
        return this.peliculaRepository.findById(id)
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    @Transactional
    public Pelicula replace(Long id, Pelicula pelicula) {
        return this.peliculaRepository.findById(id)
                .map(p -> {
                    // Aseguramos que el ID del objeto que llega sea el de la URL
                    // para evitar crear un registro nuevo por error
                    pelicula.setIdPelicula(id);
                    return this.peliculaRepository.save(pelicula);
                })
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!this.peliculaRepository.existsById(id)) {
            throw new PeliculaNotFoundException(id);
        }
        this.peliculaRepository.deleteById(id);
    }
}