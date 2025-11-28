package org.example.retofrancisco2.copias;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.retofrancisco2.peliculas.Pelicula;
import org.example.retofrancisco2.usuarios.Usuario;

/**
 * Entidad que representa una copia de una película.
 * Cada copia pertenece a un usuario y está asociada a una película,
 * además de contar con un estado y un tipo de soporte.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "copias")
public class Copia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pelicula")
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private String estado;

    private String soporte;
}
