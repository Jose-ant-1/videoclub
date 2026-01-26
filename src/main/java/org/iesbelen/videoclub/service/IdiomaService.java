package org.iesbelen.videoclub.service;

import org.iesbelen.videoclub.domain.Idioma;
import org.iesbelen.videoclub.domain.Pelicula;
import org.iesbelen.videoclub.exception.PeliculaNotFoundException;
import org.iesbelen.videoclub.repository.IdiomaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IdiomaService {

    private final IdiomaRepository idiomaRepository;

    public IdiomaService(IdiomaRepository idiomaRepository) {
        this.idiomaRepository = idiomaRepository;
    }

    public List<Idioma> all() { return this.idiomaRepository.findAll(); }

    public Idioma save(Idioma idioma) { return this.idiomaRepository.save(idioma); }

    public Idioma one(Long id) {
        return this.idiomaRepository.findById(id).orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    @Transactional
    public Idioma replace(Long id, Idioma idioma) {
        return this.idiomaRepository.findById(id)
                .map(p -> {
                    idioma.setId(id);
                    return this.idiomaRepository.save(idioma);
                })
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!this.idiomaRepository.existsById(id)) {
            throw new PeliculaNotFoundException(id);
        }
        this.idiomaRepository.deleteById(id);
    }


}
