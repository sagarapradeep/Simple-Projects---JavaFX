package lk.ijse.dep10.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void txtPassWordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {

    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.setScene(new Scene(new FXMLLoader
                (this.getClass().getResource("/view/ClientView.fxml")).load()));
        stage.show();
        stage.setResizable(true);
        stage.centerOnScreen();

    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnSignUp.getScene().getWindow();
        stage.setScene(new Scene(new FXMLLoader
                (this.getClass().getResource("/view/SignUpView.fxml")).load()));
        stage.show();
        stage.centerOnScreen();

    }

}
