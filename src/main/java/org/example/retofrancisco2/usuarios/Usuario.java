package org.example.retofrancisco2.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.retofrancisco2.copias.Copia;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="usuarios")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre_usuario;
    private String contraseña;
    private boolean administrador;
    @OneToMany(mappedBy = "usuario")
    private List<Copia> copias;
}
