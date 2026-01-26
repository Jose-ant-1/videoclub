package org.iesbelen.videoclub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    @EqualsAndHashCode.Include
    private Long id;

    private String nombre;

    // LADO INVERSO DE LA RELACIÓN N:M
    // mappedBy indica que el dueño de la relación es el atributo 'categorias' en Pelicula
    @ManyToMany(mappedBy = "categorias")
    @JsonIgnore // Fundamental para evitar que Jackson entre en bucle infinito
    private Set<Pelicula> peliculas = new HashSet<>();
}