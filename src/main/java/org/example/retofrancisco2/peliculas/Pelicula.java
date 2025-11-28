package org.example.retofrancisco2.peliculas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.retofrancisco2.copias.Copia;

import java.util.List;

/**

 * Entidad que representa una película en la base de datos.
 * Contiene información básica de la película y su relación con copias.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    private String genero;

    private Integer año;

    private String descripcion;

    private String director;

    @OneToMany(mappedBy = "pelicula")
    private List<Copia> copias;

    @Override
    public String toString() {
        return titulo;
    }
}
