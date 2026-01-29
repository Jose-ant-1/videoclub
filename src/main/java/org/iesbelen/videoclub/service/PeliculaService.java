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

    public List<Pelicula> all() {
        return this.peliculaRepository.findAll();
    }

    public Map<String,Object> all(int pagina, int tamanio) {
        Pageable paginado = PageRequest.of(pagina, tamanio, Sort.by("idPelicula").ascending());
        Page<Pelicula> pageAll = this.peliculaRepository.findAll(paginado);

        Map<String,Object> response = new HashMap<>();

        response.put("peliculas", pageAll.getContent());
        response.put("currentPage", pageAll.getNumber());
        response.put("totalItem", pageAll.getTotalElements());
        response.put("totalPages", pageAll.getTotalPages());

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