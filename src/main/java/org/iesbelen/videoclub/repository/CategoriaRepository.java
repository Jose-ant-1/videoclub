package org.iesbelen.videoclub.repository;

import org.iesbelen.videoclub.domain.Categoria;
import org.iesbelen.videoclub.domain.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    List<Categoria> findByNombreContainingIgnoreCaseOrderByNombreDesc(String nombre);

}
