package org.example.retofrancisco2.services;


import org.example.retofrancisco2.usuarios.Usuario;

public class SessionService {

    private static Usuario activeUser = null;

    public static void login(Usuario user) {
        activeUser = user;
    }

    public static boolean isLoggedIn(){
        return activeUser != null;
    }
    public static boolean isAdmin(){
        return activeUser.isAdministrador();
    }

    public static Usuario getActiveUser() {
        return activeUser;
    }

    public static void logout() {
        activeUser = null;
    }

}
