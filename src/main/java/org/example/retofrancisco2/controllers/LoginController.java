package org.example.retofrancisco2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.retofrancisco2.services.AuthService;
import org.example.retofrancisco2.services.SessionService;
import org.example.retofrancisco2.usuarios.Usuario;
import org.example.retofrancisco2.usuarios.UsuarioRepository;
import org.example.retofrancisco2.utils.DataProvider;
import org.example.retofrancisco2.utils.JavaFXUtil;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador encargado de gestionar el proceso de inicio de sesión.
 * Permite validar las credenciales del usuario, mostrar mensajes de error,
 * y redirigir a la vista correspondiente según el tipo de usuario (admin o estándar).
 */
public class LoginController implements Initializable {

    /** Campo de texto donde el usuario introduce su nombre de usuario. */
    @FXML
    private TextField tfUsuario;

    /** Campo de contraseña donde el usuario introduce su clave de acceso. */
    @FXML
    private PasswordField tfContrasena;

    /** Botón para iniciar sesión. */
    @FXML
    private Button btnIniciarSesion;

    /** Etiqueta donde se muestran mensajes informativos o de error. */
    @FXML
    private Label lblInfo;

    /** Servicio encargado de validar las credenciales y gestionar la autenticación. */
    private AuthService authService;

    /** Repositorio para acceder a usuarios almacenados en la base de datos. */
    private UsuarioRepository usuarioRepository;

    /** Botón para cancelar y salir de la aplicación. */
    @FXML
    private Button btnCancelar;

    /**
     * Inicializa el controlador instanciando el repositorio de usuarios
     * y el servicio de autenticación.
     *
     * @param url ruta del archivo FXML utilizado para cargar la vista.
     * @param resourceBundle recursos adicionales para la internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioRepository = new UsuarioRepository(DataProvider.getSessionFactory());
        authService = new AuthService(usuarioRepository);
    }

    /**
     * Valida las credenciales introducidas por el usuario e intenta iniciar sesión.
     * Si las credenciales son incorrectas, muestra un mensaje de error.
     * Si son correctas, inicia la sesión, guarda el usuario activo y redirige
     * a la vista correspondiente según su rol.
     *
     * @param actionEvent evento generado al pulsar el botón de iniciar sesión.
     */
    @FXML
    public void iniciarSesion(ActionEvent actionEvent) {
        String nombreUsuario = tfUsuario.getText();
        String password = tfContrasena.getText();

        if (!nombreUsuario.isEmpty() && !password.isEmpty()) {

            Optional<Usuario> user = authService.validateUser(nombreUsuario, password);

            if (!user.isPresent()) {
                lblInfo.setText("Usuario o contraseña incorrectos");
            } else {
                lblInfo.setText("Bienvenido " + user.get().getNombre_usuario());
                SessionService.login(user.get());

                if (SessionService.isAdmin()) {
                    JavaFXUtil.setScene("/org/example/retofrancisco2/anadir_peliculas-view.fxml");
                } else {
                    JavaFXUtil.setScene("/org/example/retofrancisco2/main-view.fxml");
                }
            }

        } else {
            lblInfo.setText("Complete los campos");
        }
    }

    /**
     * Cierra la aplicación por completo.
     *
     * @param actionEvent evento generado al pulsar el botón cancelar.
     */
    @FXML
    public void cancelar(ActionEvent actionEvent) {
        System.exit(0);
    }
}
