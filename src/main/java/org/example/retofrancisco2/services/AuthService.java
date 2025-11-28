package org.example.retofrancisco2.services;

import org.example.retofrancisco2.usuarios.Usuario;
import org.example.retofrancisco2.usuarios.UsuarioRepository;

import java.util.Optional;

/**

 * Servicio de autenticación de usuarios.
 * Permite validar las credenciales de un usuario en el sistema.
 */
public class AuthService {

    private UsuarioRepository userRepository;

    /**

     * Constructor que inicializa el servicio con un repositorio de usuarios.
     *
     * @param userRepository repositorio de usuarios.
     */
    public AuthService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**

     * Valida las credenciales de un usuario.
     *
     * @param nombreUsuario nombre de usuario a validar.
     * @param password contraseña del usuario.
     * @return un Optional con el usuario si las credenciales son correctas,
     * ```
    o Optional.empty() si no son válidas.
    ```

     */
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
