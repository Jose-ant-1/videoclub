package org.iesbelen.videoclub.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pelicula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "idPelicula")
@Data
@ToString
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    //@EqualsAndHashCode.Include // Identidad basada únicamente en el ID
    private Long idPelicula;

    private String titulo;

    private String descripcion;

    private BigDecimal precio;

    @Column(name = "anylo_lanzamiento")
    @JsonFormat(pattern = "yyyy", shape =JsonFormat.Shape.STRING)
    private Date anyloLanzamiento;

    // RELACIÓN 1:N -> Muchas películas tienen un mismo idioma
    @ManyToOne
    @JoinColumn(name = "id_idioma", nullable = false)
    private Idioma idioma;

    // RELACIÓN N:M -> Una película tiene varias categorías y viceversa
    @ManyToMany
    @JoinTable(
            name = "pelicula_categoria",
            joinColumns = @JoinColumn(name = "id_pelicula"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private Set<Categoria> categorias = new HashSet<>();

    @Column(name = "ultima_actualizacion")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date utlimaActualizacion;

}