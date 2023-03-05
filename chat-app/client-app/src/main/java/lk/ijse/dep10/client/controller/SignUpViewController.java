package lk.ijse.dep10.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class SignUpViewController {

    @FXML
    private Button btnSignUp;

    @FXML
    private DatePicker dtBirthDay;

    @FXML
    private RadioButton rdoBirthDay;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private TextField txtCounntry;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPasw;

    @FXML
    private TextField txtRePsw;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnSignUpOnAction(ActionEvent event) {

    }

}
