package org.iesbelen.videoclub.service;


import org.iesbelen.videoclub.domain.Categoria;
import org.iesbelen.videoclub.domain.CategoriaDTO;
import org.iesbelen.videoclub.exception.CategoriaNotFoundException;
import org.iesbelen.videoclub.repository.CategoriaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> all() {
        return categoriaRepository.findAll();
    }

    public Categoria save(Categoria categoria) {
        return this.categoriaRepository.save(categoria);
    }

    public Categoria one(Long id) {
        return this.categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    @Transactional
    public Categoria replace(Long id, Categoria categoria) {
        return this.categoriaRepository.findById(id).map(p -> {
            categoria.setIdCategoria(id);
            return this.categoriaRepository.save(categoria);
        }).orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!this.categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException(id);
        }
        this.categoriaRepository.deleteById(id);
    }

    public List<CategoriaDTO> findAllDTO(Optional<String> buscar, Optional<String> ordenar) {
        List<Categoria> categorias;

        if (buscar.isPresent()) {
            // Usamos los métodos que ya tienes en CategoriaRepository
            if (ordenar.isPresent() && "desc".equalsIgnoreCase(ordenar.get())) {
                categorias = categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreDesc(buscar.get());
            } else {
                categorias = categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(buscar.get());
            }
        } else {
            // Si no hay búsqueda, usamos findAll con Sort para la ordenación
            Sort sort = (ordenar.isPresent() && "desc".equalsIgnoreCase(ordenar.get()))
                    ? Sort.by("nombre").descending()
                    : Sort.by("nombre").ascending();
            categorias = categoriaRepository.findAll(sort);
        }

        // Convertimos la lista de entidades a DTOs incluyendo el conteo
        return categorias.stream()
                .map(c -> new CategoriaDTO(
                        c.getIdCategoria(),
                        c.getNombre(),
                        c.getUltimaAcutalizacion(),
                        c.getPeliculas() != null ? c.getPeliculas().size() : 0
                ))
                .collect(Collectors.toList());
    }

}
