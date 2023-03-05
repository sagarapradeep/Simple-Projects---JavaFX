package lk.ijse.dep10.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class SignUpViewController {

    public AnchorPane root;
    public PasswordField txtRePws;
    public PasswordField txtPws;
    public ComboBox cmbCountryList;
    
    @FXML
    private Button btnSignUp;

    @FXML
    private DatePicker dtBirthDay;

    @FXML
    private RadioButton rdoBirthDay;

    @FXML
    private RadioButton rdoMale;
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUserName;

    /*password field*/
    String enteredPsw = null;

    /*user defined for validation*/
    boolean isNameValid = false;
    boolean isUserNameValid = false;
    boolean isEnteredPswValid = false;


    public void initialize() {

        settingComboBox();          //setting combo box with countries

        btnSignUp.setDisable(true);     //set signup button disable

        txtName.textProperty().addListener((value, previous, current) -> {          //name validation
            txtName.getStyleClass().remove("invalid");
            String name = current.strip();

            if ((name.length() < 5) || current.isBlank()) {
                txtName.getStyleClass().add("invalid");
                return;
            }

            for (char c : name.toCharArray()) {
                if (!((Character.isLetter(c)) || (Character.isSpaceChar(c)))) {
                    System.out.println("in");
                    txtName.getStyleClass().add("invalid");
                    return;
                }
            }
            isNameValid = true;


        });     //name validation

        txtUserName.textProperty().addListener((value, previous, current) -> {      //user name validation
            txtUserName.getStyleClass().remove("invalid");
            if (current.isBlank() || current.length() < 5) {
                txtUserName.getStyleClass().add("invalid");
                return;
            }
            isUserNameValid = true;

        });     //user name validation

        txtPws.textProperty().addListener((value, previous, current) -> {
            txtPws.getStyleClass().remove("invalid");
            String password = current.strip();
            if (current.isBlank() || password.length() < 8) {
                txtPws.getStyleClass().add("invalid");
                return;
            }
            enteredPsw = current.strip();
            isEnteredPswValid = true;

        });


    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {

    }


    /*validation and pass request focus*/
    public void txtNameOnAction(ActionEvent actionEvent) {
        if (!isNameValid) {
            txtName.selectAll();
            return;
        }
        txtUserName.requestFocus();

    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        if (!isNameValid) {
            txtUserName.selectAll();
            return;
        }
        rdoMale.requestFocus();
    }

    public void txtPwsOnAction(ActionEvent actionEvent) {
        if (!isEnteredPswValid) {
            txtPws.selectAll();
            return;
        }
        txtRePws.requestFocus();
    }

    public void txtRePwsOnAction(ActionEvent actionEvent) {
        if (!Objects.equals(enteredPsw, txtRePws.getText())) {
            txtRePws.selectAll();
            txtRePws.requestFocus();
        }
    }

    public void settingComboBox() {
        String[] countryCodes = java.util.Locale.getISOCountries();
        ObservableList<String> countryList = cmbCountryList.getItems();

        for (int i = 0; i < countryCodes.length; i++) {
            Locale locale = new Locale("", countryCodes[i]);
            String name = locale.getDisplayCountry();
            countryList.add(name);

        }

    }
}

