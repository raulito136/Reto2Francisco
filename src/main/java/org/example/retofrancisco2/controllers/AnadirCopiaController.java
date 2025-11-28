package org.example.retofrancisco2.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import org.example.retofrancisco2.copias.Copia;
import org.example.retofrancisco2.copias.CopiaRepository;
import org.example.retofrancisco2.peliculas.Pelicula;
import org.example.retofrancisco2.peliculas.PeliculaRepository;
import org.example.retofrancisco2.services.SessionService;
import org.example.retofrancisco2.utils.DataProvider;
import org.example.retofrancisco2.utils.JavaFXUtil;

import javax.swing.table.*;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class AnadirCopiaController implements Initializable {
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbGenero;
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbAño;
    @javafx.fxml.FXML
    private TextField tfTitulo;
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbTitulo;
    @javafx.fxml.FXML
    private ComboBox<String> cbEstado;
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbDirector;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbDescripcion;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private ComboBox<String> cbSoporte;
    @javafx.fxml.FXML
    private TableView<Pelicula> tabla;
    PeliculaRepository peliculaRepository;
    CopiaRepository copiaRepository;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        peliculaRepository=new PeliculaRepository(DataProvider.getSessionFactory());
        copiaRepository=new CopiaRepository(DataProvider.getSessionFactory());

        cbAño.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getAño().toString());
        });
        cbGenero.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getGenero());
        });
        cbTitulo.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getTitulo());
        });
        cbDirector.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getDirector());
        });
        cbDescripcion.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getDescripcion());
        });
        ObservableList<Pelicula> observableList=observableArrayList(peliculaRepository.findAll());
        tabla.setItems(observableList);

        tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tfTitulo.setText(newValue.getTitulo());
        });
        cbEstado.getItems().addAll("bueno", "Nuevo", "Dañado");
        cbSoporte.getItems().addAll("DVD", "Blu-ray", "VHS");
    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/org/example/retofrancisco2/main-view.fxml");
    }

    @javafx.fxml.FXML
    public void añadirCopia(ActionEvent actionEvent) {
         Pelicula seleccionada = tabla.getSelectionModel().getSelectedItem();

            if (seleccionada == null) {
                JavaFXUtil.showModal(Alert.AlertType.WARNING, "Atención", null, "No has seleccionado ninguna película");
                return;
            }

            if (cbEstado.getValue() == null || cbSoporte.getValue() == null) {
                JavaFXUtil.showModal(Alert.AlertType.WARNING, "Atención", null, "Debe seleccionar estado y soporte");
                return;
            }
            Copia copia = new Copia();
            copia.setPelicula(seleccionada);
            copia.setEstado(cbEstado.getValue().toString());
            copia.setSoporte(cbSoporte.getValue().toString());
            copia.setUsuario(SessionService.getActiveUser());

            copiaRepository.save(copia);

            JavaFXUtil.setScene("/org/example/retofrancisco2/main-view.fxml");
    }

}
