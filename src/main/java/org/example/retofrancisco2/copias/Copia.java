package org.example.retofrancisco2.copias;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.retofrancisco2.peliculas.Pelicula;
import org.example.retofrancisco2.usuarios.Usuario;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="copias")
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
