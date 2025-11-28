package org.example.retofrancisco2.services;

import org.example.retofrancisco2.usuarios.Usuario;
import org.example.retofrancisco2.usuarios.UsuarioRepository;

import java.util.Optional;

public class AuthService {

    UsuarioRepository userRepository;

    public AuthService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Usuario> validateUser(String nombreUsuario, String password) {
        Optional<Usuario> user = userRepository.findByNombreUsuario(nombreUsuario);
        if (user.isPresent()) {
            if (user.get().getContraseña().equals(password)) {
                return user;
            } else  {
                return Optional.empty();
            }
        }
        return user;
    }

}
