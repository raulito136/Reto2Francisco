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

public class LoginController implements Initializable {
    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField tfContrasena;

    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Label lblInfo;

    AuthService authService;
    UsuarioRepository usuarioRepository;
    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioRepository=new UsuarioRepository(DataProvider.getSessionFactory());
        authService=new AuthService(usuarioRepository);
    }

   @FXML
   public void iniciarSesion(ActionEvent actionEvent){
        String nombreUsuario=tfUsuario.getText();
        String password=tfContrasena.getText();
        if (!nombreUsuario.isEmpty() && !password.isEmpty()){
           Optional<Usuario> user=authService.validateUser(nombreUsuario,password);
           if (!user.isPresent()){
               lblInfo.setText("Usuario o contraseña incorrectos");
           }else{
               lblInfo.setText("Bienvenido "+user.get().getNombre_usuario());
               SessionService.login(user.get());
               if (SessionService.isAdmin()){
                   JavaFXUtil.setScene("/org/example/retofrancisco2/anadir_peliculas-view.fxml");
               }else {
                   JavaFXUtil.setScene("/org/example/retofrancisco2/main-view.fxml");
               }
           }
        }else{
            lblInfo.setText("Complete los campos");
        }
   }

    @FXML
    public void cancelar(ActionEvent actionEvent) {
        System.exit(0);
    }
}
