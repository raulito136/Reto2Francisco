package org.example.retofrancisco2.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import org.example.retofrancisco2.peliculas.Pelicula;
import org.example.retofrancisco2.peliculas.PeliculaRepository;
import org.example.retofrancisco2.services.AuthService;
import org.example.retofrancisco2.services.SessionService;
import org.example.retofrancisco2.utils.DataProvider;
import org.example.retofrancisco2.utils.JavaFXUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class AnadirPeliculasController implements Initializable {
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcAno;
    @javafx.fxml.FXML
    private MenuItem miCerrarSesion;
    @javafx.fxml.FXML
    private TextField tfDescripcion;
    @javafx.fxml.FXML
    private MenuItem miCerrar;
    @javafx.fxml.FXML
    private TextField tfGenero;
    @javafx.fxml.FXML
    private Spinner spnAno;
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcTitulo;
    @javafx.fxml.FXML
    private TextField tfDirector;
    @javafx.fxml.FXML
    private TextField tfTitulo;
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcDescripcion;
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcDirector;
    @javafx.fxml.FXML
    private TableView<Pelicula> tabla;
    @javafx.fxml.FXML
    private Button btnAnadir;
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcId;
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcGenero;
   PeliculaRepository peliculaRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        peliculaRepository=new PeliculaRepository(DataProvider.getSessionFactory());
        tcId.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getId().toString());
        });
        tcTitulo.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getTitulo());
        });
        tcGenero.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getGenero());
        });
        tcAno.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getAño().toString());
        });
        tcDescripcion.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getDescripcion());
        });
        tcDirector.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getDirector());
        });
        spnAno.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 2025, 2023));
        ObservableList<Pelicula> observableList=observableArrayList(peliculaRepository.findAll());
        tabla.setItems(observableList);

    }

    @javafx.fxml.FXML
    public void cerrarSesion(ActionEvent actionEvent) {
        SessionService.logout();
        JavaFXUtil.setScene("/org/example/retofrancisco2/login-view.fxml");
    }

    @javafx.fxml.FXML
    public void añadirPelicula(ActionEvent actionEvent) {
        Pelicula pelicula=new Pelicula();
        if (tfTitulo.getText().isEmpty() || tfGenero.getText().isEmpty() || tfDescripcion.getText().isEmpty() || tfDirector.getText().isEmpty()){
            JavaFXUtil.showModal(Alert.AlertType.WARNING, "Atención", null, "Debe rellenar todos los campos");
        }else{
            pelicula.setTitulo(tfTitulo.getText());
            pelicula.setGenero(tfGenero.getText());
            pelicula.setAño((Integer) spnAno.getValue());
            pelicula.setDescripcion(tfDescripcion.getText());
            pelicula.setDirector(tfDirector.getText());
            peliculaRepository.save(pelicula);
            tabla.getItems().add(pelicula);
        }
    }

    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        System.exit(0);
    }
}
