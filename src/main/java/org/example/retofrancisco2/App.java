package org.example.retofrancisco2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.retofrancisco2.utils.JavaFXUtil;

import java.io.IOException;

/**

 * Clase principal de la aplicación JavaFX.
 * Inicializa la ventana principal y carga la vista de inicio de sesión.
 */
public class App extends Application {

    /**

     * Método llamado automáticamente al iniciar la aplicación JavaFX.
     *
     * @param stage la ventana principal proporcionada por JavaFX.
     * @throws IOException si ocurre un error al cargar la vista FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        JavaFXUtil.initStage(stage);
        JavaFXUtil.setScene("/org/example/retofrancisco2/login-view.fxml");
    }

    /**

     * Método de entrada de la aplicación.
     *
     * @param args argumentos de línea de comandos (no usados).
     */
    public static void main(String[] args) {
        launch();
    }
}
