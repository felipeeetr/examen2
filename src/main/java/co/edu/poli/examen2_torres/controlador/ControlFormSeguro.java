package co.edu.poli.examen2_torres.controlador;
 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
 
import co.edu.poli.examen2_torres.modelo.asegurado;
import co.edu.poli.examen2_torres.modelo.seguro;
import co.edu.poli.examen2_torres.modelo.seguroVehiculo;
import co.edu.poli.examen2_torres.modelo.seguroVida;
import co.edu.poli.examen2_torres.servicios.DAOasegurado;
import co.edu.poli.examen2_torres.servicios.DAOseguro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.application.Platform;
 
public class ControlFormSeguro {
 
    @FXML
    private Button bttConsulta;
    @FXML
    private TextField txtSeguro1;
    @FXML
    private TextArea txtAreaResultado;
    @FXML
    private Button bttCreacion;
    @FXML
    private TextField txtSeguro2;
    @FXML
    private DatePicker datepk1;
    @FXML
    private ComboBox<asegurado> cmbAsegurado;
    @FXML
    private RadioButton radio1; // Seguro Vida
    @FXML
    private RadioButton radio2; // Seguro Vehiculo
    @FXML
    private ToggleGroup tipo;
 
    private DAOseguro daoSeguro;
    private DAOasegurado daoAsegurado;
 
    @FXML
    private void initialize() {
        daoSeguro = new DAOseguro();
        daoAsegurado = new DAOasegurado();
 
        datepk1.setValue(LocalDate.now());
 
        List<asegurado> lista = null;
        try {
            lista = daoAsegurado.readall();
        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }
        cmbAsegurado.getItems().setAll(lista);
 
        txtSeguro1.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal)
                validarSoloNumeros(txtSeguro1);
        });
 
        txtSeguro2.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal)
                validarSoloNumeros(txtSeguro2);
        });
    }
 
    @FXML
    private void pressConsulta(ActionEvent event) {
        txtAreaResultado.setText("");
        if (!txtSeguro1.getText().isBlank() || !txtSeguro1.getText().isEmpty()) {
            try {
                seguro s = daoSeguro.readone(txtSeguro1.getText());
 
                if (s != null)
                    txtAreaResultado.setText(s.toString());
                else
                    mostrarAlerta("No existe el número de seguro");
            } catch (Exception e) {
                mostrarAlerta(e.getMessage());
            }
        } else
            mostrarAlerta("Ingrese número de seguro");
    }
 
    @FXML
    private void pressCreacion(ActionEvent event) {
 
        String numero = txtSeguro2.getText().trim();
        if (numero.isEmpty()) {
            mostrarAlerta("⚠ Ingrese el número de seguro.");
            return;
        }
 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaExp = datepk1.getValue().format(formatter);
        if (fechaExp == null || fechaExp.isEmpty()) {
            mostrarAlerta("⚠ Seleccione la fecha de expedición.");
            return;
        }
 
        asegurado asegurado = cmbAsegurado.getValue();
        if (asegurado == null) {
            mostrarAlerta("⚠ Seleccione un asegurado.");
            return;
        }
 
        seguro nuevo;
 
        if (radio1.isSelected()) {
            nuevo = new seguroVida(numero, fechaExp, true, asegurado, "");
        } else {
            nuevo = new seguroVehiculo(numero, fechaExp, true, asegurado, "");
        }
 
        try {
            String resultado = daoSeguro.create(nuevo);
            mostrarAlerta(resultado);
            if (resultado.startsWith("✔")) {
                limpiarFormCrear();
            }
        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }
    }
 
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
 
    private void limpiarFormCrear() {
        txtSeguro2.clear();
        datepk1.setValue(null);
        cmbAsegurado.setValue(null);
        radio1.setSelected(true);
    }
 
    private void validarSoloNumeros(TextField txt) {
        String texto = txt.getText().trim();
        if (!texto.isBlank()) {
            if (!texto.matches("\\d+")) {
                txtAreaResultado.setText("");
                txt.setStyle("-fx-border-color: red;");
                mostrarAlerta("Solo números son permitidos!");
                txt.setText("");
                Platform.runLater(() -> txt.requestFocus());
            } else {
                txt.setStyle("");
            }
        } else
            txt.setStyle("");
    }
}
