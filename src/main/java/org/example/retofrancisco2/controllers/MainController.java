package org.example.retofrancisco2.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.example.retofrancisco2.copias.Copia;
import org.example.retofrancisco2.copias.CopiaRepository;
import org.example.retofrancisco2.services.CopiaService;
import org.example.retofrancisco2.services.SessionService;
import org.example.retofrancisco2.utils.DataProvider;
import org.example.retofrancisco2.utils.JavaFXUtil;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Controlador principal de la interfaz del usuario estándar.
 * Permite gestionar las copias pertenecientes al usuario activo y realizar acciones
 * como añadir, editar, ver y eliminar copias, así como gestionar el cierre de sesión.
 */
public class MainController implements Initializable {

    @FXML
    private Pane root;

    /** Opción del menú para cerrar sesión. */
    @FXML
    private MenuItem cerrarSesion;

    /** Botón para eliminar una copia seleccionada. */
    @FXML
    private Button btnEliminar;

    /** Opción del menú para cerrar la aplicación. */
    @FXML
    private MenuItem cerrar;

    /** Tabla donde se muestran las copias del usuario activo. */
    @FXML
    private TableView<Copia> tablaCopias;

    /** Barra de menú de la interfaz. */
    @FXML
    private MenuBar menu;

    /** Botón para añadir una nueva copia. */
    @FXML
    private Button btnAñadir;

    /** Repositorio para operaciones de persistencia con copias. */
    private CopiaRepository copiaRepository;

    /** ComboBox para seleccionar el estado de la copia seleccionada. */
    @FXML
    private ComboBox cbEstado;

    /** Columna que muestra el título de la película asociada a la copia. */
    @FXML
    private TableColumn<Copia, String> tcTitulo;

    /** Columna que muestra el tipo de soporte de la copia. */
    @FXML
    private TableColumn<Copia, String> tcTipo;

    /** Botón para editar los datos de la copia seleccionada. */
    @FXML
    private Button btnEditar;

    /** Columna que muestra el estado de la copia. */
    @FXML
    private TableColumn<Copia, String> tcEstado;

    /** ComboBox para seleccionar el soporte de la copia seleccionada. */
    @FXML
    private ComboBox cbTipo;

    /** Campo de texto que muestra el título de la copia seleccionada. */
    @FXML
    private TextField tfTitulo;

    /** Botón para ver los detalles de la copia seleccionada. */
    @FXML
    private Button btnVerCopia;

    /**
     * Inicializa la vista cargando las copias del usuario, configurando las columnas
     * de la tabla y activando/desactivando controles según corresponda.
     *
     * @param url ruta del FXML cargado.
     * @param resourceBundle recursos de idioma o configuración.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        copiaRepository = new CopiaRepository(DataProvider.getSessionFactory());

        tcTitulo.setCellValueFactory(row ->
                new SimpleStringProperty(row.getValue().getPelicula().toString()));

        tcEstado.setCellValueFactory(row ->
                new SimpleStringProperty(row.getValue().getEstado()));

        tcTipo.setCellValueFactory(row ->
                new SimpleStringProperty(row.getValue().getSoporte()));

        ObservableList<Copia> copias =
                observableArrayList(copiaRepository.findByUsuarioId(
                        Long.valueOf(SessionService.getActiveUser().getId())
                ));
        tablaCopias.setItems(copias);

        tablaCopias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfTitulo.setText(newValue.getPelicula().getTitulo());
                cbEstado.setValue(newValue.getEstado());
                cbTipo.setValue(newValue.getSoporte());

                btnEditar.setDisable(false);
                cbEstado.setDisable(false);
                cbTipo.setDisable(false);
                btnVerCopia.setDisable(false);
                if (btnEliminar != null) btnEliminar.setDisable(false);
            } else {
                limpiarFormulario();
            }
        });

        tablaCopias.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();
            boolean clickEnFilaValida = false;
            Node node = source;
            while (node != null && node != tablaCopias) {
                if (node instanceof TableRow && !((TableRow<?>) node).isEmpty()) {
                    clickEnFilaValida = true;
                    break;
                }
                node = node.getParent();
            }
            if (!clickEnFilaValida) {
                tablaCopias.getSelectionModel().clearSelection();
            }
        });

        if (root != null) {
            root.setOnMouseClicked(event -> {
                Node source = (Node) event.getTarget();
                boolean clicEnZonaSegura = false;
                Node nodoActual = source;
                while (nodoActual != null) {
                    if (nodoActual == tablaCopias ||
                            nodoActual instanceof Button ||
                            nodoActual instanceof TextField ||
                            nodoActual instanceof ComboBox ||
                            nodoActual instanceof MenuBar) {

                        clicEnZonaSegura = true;
                        break;
                    }
                    nodoActual = nodoActual.getParent();
                }

                if (!clicEnZonaSegura) {
                    tablaCopias.getSelectionModel().clearSelection();
                }
            });
        }

        limpiarFormulario();

        cbEstado.getItems().addAll("bueno", "Nuevo", "Dañado");
        cbTipo.getItems().addAll("DVD", "Blu-ray", "VHS");
    }

    /**
     * Limpia los campos del formulario y deshabilita los botones de acción.
     */
    private void limpiarFormulario() {
        tfTitulo.setText("");
        cbEstado.setValue(null);
        cbTipo.setValue(null);

        btnEditar.setDisable(true);
        cbEstado.setDisable(true);
        cbTipo.setDisable(true);
        btnVerCopia.setDisable(true);
        if (btnEliminar != null) btnEliminar.setDisable(true);
    }

    /**
     * Cambia la vista a la pantalla para añadir una nueva copia.
     *
     * @param actionEvent evento generado al pulsar el botón.
     */
    @FXML
    public void añadirCopia(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/org/example/retofrancisco2/anadir_copia-view.fxml");
    }

    /**
     * Elimina la copia seleccionada tras confirmar la acción con el usuario.
     * Muestra advertencias si no hay selección.
     *
     * @param actionEvent evento generado al pulsar el botón de eliminar.
     */
    @FXML
    public void eliminarCopia(ActionEvent actionEvent) {
        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar copia");
            alert.setHeaderText("¿Desea eliminar la copia?");
            String titulo = seleccionada.getPelicula() != null
                    ? seleccionada.getPelicula().getTitulo()
                    : "Desconocido";
            alert.setContentText("Desea eliminar la copia de la película: " + titulo + "?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                copiaRepository.delete(seleccionada);
                tablaCopias.getItems().remove(seleccionada);
            }

        } else {
            JavaFXUtil.showModal(Alert.AlertType.WARNING, "Atención", null, "No has seleccionado ninguna copia");
        }
    }

    /**
     * Cierra la sesión actual y redirige al usuario a la pantalla de inicio de sesión.
     *
     * @param actionEvent evento generado al seleccionar la opción en el menú.
     */
    @FXML
    public void cerrarSesion(ActionEvent actionEvent) {
        SessionService.logout();
        JavaFXUtil.setScene("/org/example/retofrancisco2/login-view.fxml");
    }

    /**
     * Cierra completamente la aplicación.
     *
     * @param actionEvent evento generado al pulsar la opción de cerrar.
     */
    @FXML
    public void cerrar(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Permite editar el estado y el soporte de la copia seleccionada.
     * Si hay campos sin rellenar, muestra una advertencia.
     *
     * @param actionEvent evento generado al pulsar el botón de editar.
     */
    @FXML
    public void editarCopia(ActionEvent actionEvent) {
        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();

        if (seleccionada == null) return; // Seguridad extra

        if (tfTitulo.getText().isEmpty() || cbEstado.getValue() == null || cbTipo.getValue() == null) {
            JavaFXUtil.showModal(Alert.AlertType.WARNING, "Atención", null, "Debe rellenar todos los campos");
        } else {
            seleccionada.setEstado(cbEstado.getValue().toString());
            seleccionada.setSoporte(cbTipo.getValue().toString());
            copiaRepository.edit(seleccionada);
            tablaCopias.refresh();
        }
    }

    /**
     * Abre la vista de detalles de la copia seleccionada.
     * Para ello, guarda primero la copia activa en el servicio CopiaService.
     *
     * @param actionEvent evento generado al pulsar el botón de ver copia.
     */
    @FXML
    public void verCopia(ActionEvent actionEvent) {
        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            CopiaService.getInstance().setCopiaSeleccionada(seleccionada);
            JavaFXUtil.setScene("/org/example/retofrancisco2/ver_copia-view.fxml");
        }
    }
}