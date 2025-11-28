package org.example.retofrancisco2.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**

 * Clase de utilidad para gestionar la ventana y escenas de JavaFX.
 * Permite inicializar el stage principal, cambiar escenas y mostrar modales.
 */
public class JavaFXUtil {

    private static Stage stage;

    /**

     * Constructor privado para evitar instanciación.
     */
    private JavaFXUtil() {}

    /**

     * Inicializa el stage principal de la aplicación.
     *
     * @param stage el stage principal de JavaFX.
     */
    public static void initStage(Stage stage) {
        JavaFXUtil.stage = stage;
    }

    /**

     * Obtiene el stage principal de la aplicación.
     *
     * @return el stage principal.
     */
    public static Stage getStage() {
        return stage;
    }

    /**

     * Cambia la escena del stage principal cargando un archivo FXML.
     *
     * @param fxml la ruta del archivo FXML.
     * @param <T> el tipo de controlador asociado al FXML.
     * @return el controlador de la nueva escena, o null si ocurre un error.
     */
    public static <T> T setScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXUtil.class.getResource(fxml));
            Parent root = loader.load();
            T controller = loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            stage.setResizable(false);
            return controller;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**

     * Muestra un modal de alerta en la aplicación.
     *
     * @param type el tipo de alerta.
     * @param title el título de la ventana de alerta.
     * @param header el texto del encabezado de la alerta.
     * @param content el contenido del mensaje de la alerta.
     */
    public static void showModal(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(stage);
        alert.showAndWait();
    }
}
