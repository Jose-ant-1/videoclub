package org.iesbelen.videoclub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "idioma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_idioma")
    //@EqualsAndHashCode.Include
    private Long id;

    private String nombre;

    // LADO INVERSO DE LA RELACIÓN
    // 'mappedBy' indica el nombre del atributo en la clase Pelicula
    @OneToMany(mappedBy = "idioma")
    @JsonIgnore // Evita que al pedir un idioma se genere un bucle infinito de JSON
    @ToString.Exclude
    private List<Pelicula> peliculaIdioma;

    /*
    @OneToMany(mappedBy = "idiomaOriginal")
    @JsonIgnore
    @ToString.Exclude
    private List<Pelicula> peliculasIdiomaOriginal;
*/


}
