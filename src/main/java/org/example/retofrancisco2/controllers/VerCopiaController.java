package org.example.retofrancisco2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.example.retofrancisco2.copias.Copia;
import org.example.retofrancisco2.services.CopiaService;
import org.example.retofrancisco2.utils.JavaFXUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador encargado de mostrar la información detallada
 * de una copia seleccionada por el usuario. Esta vista es de solo lectura
 * y permite regresar al menú principal.
 */
public class VerCopiaController implements Initializable {

    /** Texto donde se muestra el estado de la copia. */
    @javafx.fxml.FXML
    private Text lblEstado;

    /** Texto donde se muestra el ID de la copia. */
    @javafx.fxml.FXML
    private Text lblId;

    /** Texto donde se muestra el propietario de la copia. */
    @javafx.fxml.FXML
    private Text lblUsuario;

    /** Texto donde se muestra el título de la película asociada a la copia. */
    @javafx.fxml.FXML
    private Text lblPelicula;

    /** Botón para volver a la pantalla principal. */
    @javafx.fxml.FXML
    private Button btnVolver;

    /** Texto donde se muestra el soporte físico de la copia (DVD, Blu-ray, VHS...). */
    @javafx.fxml.FXML
    private Text lblSoporte;

    /**
     * Inicializa la vista cargando los datos de la copia seleccionada.
     *
     * @param url ubicación utilizada para resolver rutas relativas (no utilizada).
     * @param resourceBundle recursos internacionalizados (no utilizado).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Obtiene la copia seleccionada desde el servicio singleton.
        Copia copia = CopiaService.getInstance().getCopiaSeleccionada();

        // Rellena los campos con los datos correspondientes.
        lblId.setText("ID: " + copia.getId());
        lblPelicula.setText("Pelicula: " + copia.getPelicula().getTitulo());
        lblUsuario.setText("Propietario: " + copia.getUsuario().getNombre_usuario());
        lblEstado.setText("Estado: " + copia.getEstado());
        lblSoporte.setText("Soporte: " + copia.getSoporte());
    }

    /**
     * Acción ejecutada al pulsar el botón "Volver".
     * Retorna al usuario a la pantalla principal.
     *
     * @param actionEvent evento generado por la acción del botón.
     */
    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/org/example/retofrancisco2/main-view.fxml");
    }
}
