package org.iesbelen.videoclub.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    private Long idCategoria;
    private String nombre;
    private Date ultimaAcutalizacion;
    private int conteoPeliculas; // Campo calculado
}