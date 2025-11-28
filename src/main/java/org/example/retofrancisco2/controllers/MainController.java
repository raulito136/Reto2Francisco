package org.example.retofrancisco2.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class MainController implements Initializable {
    @javafx.fxml.FXML
    private MenuItem cerrarSesion;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private MenuItem cerrar;
    @javafx.fxml.FXML
    private TableView<Copia> tablaCopias;
    @javafx.fxml.FXML
    private MenuBar menu;
    @javafx.fxml.FXML
    private Button btnAñadir;
    CopiaRepository copiaRepository;
    @FXML
    private ComboBox cbEstado;
    @FXML
    private TableColumn<Copia,String> tcTitulo;
    @FXML
    private TableColumn<Copia,String> tcTipo;
    @FXML
    private Button btnEditar;
    @FXML
    private TableColumn<Copia,String> tcEstado;
    @FXML
    private ComboBox cbTipo;
    @FXML
    private TextField tfTitulo;
    @FXML
    private Button btnVerCopia;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        copiaRepository=new CopiaRepository(DataProvider.getSessionFactory());
        tcTitulo.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getPelicula().toString());
        });

        tcEstado.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getEstado());
        });
        tcTipo.setCellValueFactory(row->{
            return new SimpleStringProperty(row.getValue().getSoporte());
        });
        ObservableList<Copia> copias = observableArrayList(copiaRepository.findByUsuarioId(Long.valueOf(SessionService.getActiveUser().getId())));
        tablaCopias.setItems(copias);

        tablaCopias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tfTitulo.setText(newValue.getPelicula().getTitulo());
            cbEstado.setValue(newValue.getEstado());
            cbTipo.setValue(newValue.getSoporte());
            btnEditar.setDisable(false);
            cbEstado.setDisable(false);
            cbTipo.setDisable(false);
            btnVerCopia.setDisable(false);
        });
        if (tfTitulo.getText().isEmpty()){
            btnEditar.setDisable(true);
            cbEstado.setDisable(true);
            cbTipo.setDisable(true);
            btnVerCopia.setDisable(true);
        }
        cbEstado.getItems().addAll("bueno", "Nuevo", "Dañado");
        cbTipo.getItems().addAll("DVD", "Blu-ray", "VHS");
    }

    @javafx.fxml.FXML
    public void añadirCopia(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/org/example/retofrancisco2/anadir_copia-view.fxml");
    }

    @FXML
    public void eliminarCopia(ActionEvent actionEvent) {
        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar copia");
            alert.setHeaderText("¿Desea eliminar la copia?");
            String titulo = seleccionada.getPelicula() != null ? seleccionada.getPelicula().getTitulo() : "Desconocido";
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

    @javafx.fxml.FXML
    public void cerrarSesion(ActionEvent actionEvent) {
        SessionService.logout();
        JavaFXUtil.setScene("/org/example/retofrancisco2/login-view.fxml");
    }
    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void editarCopia(ActionEvent actionEvent) {
        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();
        if (tfTitulo.getText().isEmpty() || cbEstado.getValue() == null || cbTipo.getValue() == null){
            JavaFXUtil.showModal(Alert.AlertType.WARNING, "Atención", null, "Debe rellenar todos los campos");
        }else{
            seleccionada.setEstado(cbEstado.getValue().toString());
            seleccionada.setSoporte(cbTipo.getValue().toString());
            copiaRepository.edit(seleccionada);
            tablaCopias.refresh();
        }
    }

    @FXML
    public void verCopia(ActionEvent actionEvent) {
        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();
        CopiaService.getInstance().setCopiaSeleccionada(seleccionada);
        JavaFXUtil.setScene("/org/example/retofrancisco2/ver_copia-view.fxml");

    }
}



