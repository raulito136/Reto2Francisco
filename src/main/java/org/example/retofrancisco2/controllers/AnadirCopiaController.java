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

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Controlador encargado de gestionar la vista para añadir nuevas copias de películas.
 * Permite seleccionar una película de una tabla e introducir los datos necesarios
 * (estado y soporte) para crear y guardar una nueva copia en la base de datos.
 */
public class AnadirCopiaController implements Initializable {

    /** Columna que muestra el género de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbGenero;

    /** Columna que muestra el año de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbAño;

    /** Campo de texto donde se muestra el título de la película seleccionada. */
    @javafx.fxml.FXML
    private TextField tfTitulo;

    /** Columna que muestra el título de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbTitulo;

    /** ComboBox que permite seleccionar el estado de la copia. */
    @javafx.fxml.FXML
    private ComboBox<String> cbEstado;

    /** Columna que muestra el director de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbDirector;

    /** Botón para cancelar la operación. */
    @javafx.fxml.FXML
    private Button btnCancelar;

    /** Columna que muestra la descripción de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> cbDescripcion;

    /** Botón para añadir la copia. */
    @javafx.fxml.FXML
    private Button btnAñadir;

    /** ComboBox que permite seleccionar el tipo de soporte de la copia. */
    @javafx.fxml.FXML
    private ComboBox<String> cbSoporte;

    /** Tabla que muestra todas las películas disponibles. */
    @javafx.fxml.FXML
    private TableView<Pelicula> tabla;

    /** Repositorio para gestionar las operaciones relacionadas con películas. */
    private PeliculaRepository peliculaRepository;

    /** Repositorio para gestionar las operaciones relacionadas con copias. */
    private CopiaRepository copiaRepository;

    /**
     * Inicializa la vista, cargando las películas en la tabla,
     * configurando las columnas y preparando los ComboBox.
     *
     * @param url URL de inicialización.
     * @param resourceBundle Recursos adicionales.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        peliculaRepository = new PeliculaRepository(DataProvider.getSessionFactory());
        copiaRepository = new CopiaRepository(DataProvider.getSessionFactory());

        cbAño.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getAño().toString()));
        cbGenero.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getGenero()));
        cbTitulo.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getTitulo()));
        cbDirector.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getDirector()));
        cbDescripcion.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getDescripcion()));

        ObservableList<Pelicula> observableList = observableArrayList(peliculaRepository.findAll());
        tabla.setItems(observableList);

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) tfTitulo.setText(newValue.getTitulo());
        });

        cbEstado.getItems().addAll("bueno", "Nuevo", "Dañado");
        cbSoporte.getItems().addAll("DVD", "Blu-ray", "VHS");
    }

    /**
     * Acción que cancela el proceso y vuelve a la vista principal.
     *
     * @param actionEvent evento de acción generado por el botón.
     */
    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/org/example/retofrancisco2/main-view.fxml");
    }

    /**
     * Acción que crea y guarda una nueva copia basada en la película seleccionada.
     * Verifica que haya selección y que los campos necesarios estén completados.
     *
     * @param actionEvent evento de acción generado por el botón.
     */
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
        copia.setEstado(cbEstado.getValue());
        copia.setSoporte(cbSoporte.getValue());
        copia.setUsuario(SessionService.getActiveUser());

        copiaRepository.save(copia);

        JavaFXUtil.showModal(Alert.AlertType.INFORMATION, "Éxito", null, "Copia añadida correctamente");

        JavaFXUtil.setScene("/org/example/retofrancisco2/main-view.fxml");
    }

}
