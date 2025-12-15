package org.example.retofrancisco2.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import org.example.retofrancisco2.peliculas.Pelicula;
import org.example.retofrancisco2.peliculas.PeliculaRepository;
import org.example.retofrancisco2.services.SessionService;
import org.example.retofrancisco2.utils.DataProvider;
import org.example.retofrancisco2.utils.JavaFXUtil;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Controlador encargado de gestionar la vista para añadir nuevas películas al sistema.
 * Permite al administrador introducir los datos de una película a través de un formulario
 * y mostrarlas en una tabla tras ser guardadas en la base de datos.
 */
public class AnadirPeliculasController implements Initializable {

    /** Columna que muestra el año de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcAno;

    /** Elemento del menú para cerrar sesión. */
    @javafx.fxml.FXML
    private MenuItem miCerrarSesion;

    /** Campo de texto que contiene la descripción de la película. */
    @javafx.fxml.FXML
    private TextField tfDescripcion;

    /** Elemento del menú para cerrar la aplicación. */
    @javafx.fxml.FXML
    private MenuItem miCerrar;

    /** Campo de texto que contiene el género de la película. */
    @javafx.fxml.FXML
    private TextField tfGenero;

    /** Spinner para seleccionar el año de la película. */
    @javafx.fxml.FXML
    private Spinner spnAno;

    /** Columna que muestra el título de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcTitulo;

    /** Campo de texto que contiene el director de la película. */
    @javafx.fxml.FXML
    private TextField tfDirector;

    /** Campo de texto que contiene el título de la película. */
    @javafx.fxml.FXML
    private TextField tfTitulo;

    /** Columna que muestra la descripción de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcDescripcion;

    /** Columna que muestra el director de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcDirector;

    /** Tabla donde se muestran todas las películas registradas. */
    @javafx.fxml.FXML
    private TableView<Pelicula> tabla;

    /** Botón para añadir una nueva película. */
    @javafx.fxml.FXML
    private Button btnAnadir;

    /** Columna que muestra el ID de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcId;

    /** Columna que muestra el género de la película. */
    @javafx.fxml.FXML
    private TableColumn<Pelicula,String> tcGenero;

    /** Repositorio encargado de gestionar operaciones de persistencia con películas. */
    private PeliculaRepository peliculaRepository;

    /**
     * Inicializa la interfaz configurando las columnas de la tabla,
     * el spinner de año y cargando las películas existentes desde la base de datos.
     *
     * @param url ubicación utilizada para resolver rutas relativas del archivo FXML.
     * @param resourceBundle recursos adicionales para la internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        peliculaRepository = new PeliculaRepository(DataProvider.getSessionFactory());

        tcId.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getId().toString()));
        tcTitulo.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getTitulo()));
        tcGenero.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getGenero()));
        tcAno.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getAño().toString()));
        tcDescripcion.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getDescripcion()));
        tcDirector.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getDirector()));

        spnAno.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 2025, 2023));

        limitarLongitud(tfTitulo, 255);
        limitarLongitud(tfGenero, 255);
        limitarLongitud(tfDirector, 255);
        limitarLongitud(tfDescripcion, 255);

        ObservableList<Pelicula> observableList = observableArrayList(peliculaRepository.findAll());
        tabla.setItems(observableList);
    }

    /**
     * Cierra la sesión actual del usuario y redirige a la vista de inicio de sesión.
     *
     * @param actionEvent evento generado al seleccionar la opción en el menú.
     */
    @javafx.fxml.FXML
    public void cerrarSesion(ActionEvent actionEvent) {
        SessionService.logout();
        JavaFXUtil.setScene("/org/example/retofrancisco2/login-view.fxml");
    }

    /**
     * Añade una nueva película a la base de datos utilizando los valores
     * ingresados en el formulario. Si algún campo está vacío, muestra una advertencia.
     *
     * @param actionEvent evento generado al pulsar el botón para añadir película.
     */
    @javafx.fxml.FXML
    public void añadirPelicula(ActionEvent actionEvent) {
        String titulo = tfTitulo.getText().trim();
        String genero = tfGenero.getText().trim();
        String descripcion = tfDescripcion.getText().trim();
        String director = tfDirector.getText().trim();

        if (titulo.isEmpty() || genero.isEmpty() || descripcion.isEmpty() || director.isEmpty()) {

            JavaFXUtil.showModal(Alert.AlertType.WARNING, "Atención", null, "Debe rellenar todos los campos y no pueden contener solo espacios.");
            return;

        }

        if (esSoloNumero(genero) || esSoloNumero(descripcion) || esSoloNumero(director)) {

            JavaFXUtil.showModal(Alert.AlertType.WARNING, "Atención", null, "Los campos Género, Descripción y Director no pueden contener únicamente un valor numérico.");
            return;
        }

        Pelicula pelicula = new Pelicula();

        pelicula.setTitulo(titulo);
        pelicula.setGenero(genero);
        pelicula.setAño((Integer) spnAno.getValue());
        pelicula.setDescripcion(descripcion);
        pelicula.setDirector(director);

        peliculaRepository.save(pelicula);
        tabla.getItems().add(pelicula);

        JavaFXUtil.showModal(Alert.AlertType.INFORMATION, "Éxito", null, "Película añadida correctamente");

        // Limpieza de campos
        tfTitulo.clear();
        tfGenero.clear();
        tfDescripcion.clear();
        tfDirector.clear();
    }

    /**
     * Cierra la aplicación por completo.
     *
     * @param actionEvent evento generado al seleccionar la opción de cerrar.
     */
    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     *Metodo para liminar el numero de caracteres, pricipalmente para que no sobrepase los permitidos por la BD
     *
     * @param tf El textfield al que se le aplicará la limitación de longitud.
     * @param maxLength La longitud máxima de caracteres permitida en el campo de texto.
     */

    private void limitarLongitud(TextField tf, int maxLength) {
        tf.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > maxLength) {
                return null; // Rechaza el cambio si se pasa de 255
            }
            return change; // Acepta el cambio
        }));
    }

    /**
     * Verifica si la cadena de entrada contiene solo caracteres numéricos enteros.
     *
     * @param str La cadena a validar (ya debe estar limpia de espacios con trim()).
     * @return true si la cadena es un número entero (ej: "123", "0", "-5"), false en caso contrario.
     */
    private boolean esSoloNumero(String str) {
        return Pattern.matches("^[+-]?\\d+$", str);
    }
}
