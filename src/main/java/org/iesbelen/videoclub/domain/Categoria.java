package org.iesbelen.videoclub.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "nombre")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    //@EqualsAndHashCode.Include
    private Long idCategoria;
    @NaturalId
    private String nombre;

    // LADO INVERSO DE LA RELACIÓN N:M
    // mappedBy indica que el dueño de la relación es el atributo 'categorias' en Pelicula
    @ManyToMany(mappedBy = "categorias")
    @ToString.Exclude
    @JsonIgnore // Fundamental para evitar que Jackson entre en bucle infinito
    private Set<Pelicula> peliculas = new HashSet<>();

    @Column(name = "ultima_actualizacion")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date ultimaAcutalizacion;
}