package org.example.retofrancisco2.services;

import org.example.retofrancisco2.usuarios.Usuario;

/**

 * Servicio que maneja la sesión del usuario actualmente activo.
 * Permite iniciar sesión, cerrar sesión y consultar el usuario activo.
 */
public class SessionService {

    private static Usuario activeUser = null;

    /**

     * Inicia sesión con el usuario proporcionado.
     *
     * @param user el usuario que inicia sesión.
     */
    public static void login(Usuario user) {
        activeUser = user;
    }

    /**

     * Verifica si hay un usuario actualmente logueado.
     *
     * @return true si hay un usuario activo, false en caso contrario.
     */
    public static boolean isLoggedIn() {
        return activeUser != null;
    }

    /**

     * Verifica si el usuario actualmente logueado es administrador.
     *
     * @return true si el usuario activo es administrador, false en caso contrario.
     */
    public static boolean isAdmin() {
        return activeUser != null && activeUser.isAdministrador();
    }

    /**

     * Obtiene el usuario actualmente logueado.
     *
     * @return el usuario activo, o null si no hay ninguno.
     */
    public static Usuario getActiveUser() {
        return activeUser;
    }

    /**

     * Cierra la sesión del usuario actualmente activo.
     */
    public static void logout() {
        activeUser = null;
    }
}
