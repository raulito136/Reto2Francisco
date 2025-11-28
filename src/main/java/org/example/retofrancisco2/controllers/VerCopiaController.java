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

public class VerCopiaController implements Initializable {
    @javafx.fxml.FXML
    private Text lblEstado;
    @javafx.fxml.FXML
    private Text lblId;
    @javafx.fxml.FXML
    private Text lblUsuario;
    @javafx.fxml.FXML
    private Text lblPelicula;
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private Text lblSoporte;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Copia copia=CopiaService.getInstance().getCopiaSeleccionada();
        lblId.setText("ID: "+ copia.getId());
        lblPelicula.setText("Pelicula: "+copia.getPelicula().getTitulo());
        lblUsuario.setText("Propietario: "+copia.getUsuario().getNombre_usuario());
        lblEstado.setText("Estado: "+copia.getEstado());
        lblSoporte.setText("Soporte: "+copia.getSoporte());
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/org/example/retofrancisco2/main-view.fxml");
    }
}
