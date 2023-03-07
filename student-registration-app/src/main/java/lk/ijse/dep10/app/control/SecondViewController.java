package lk.ijse.dep10.app.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SecondViewController {

    public TextField txtGuardianNIC;
    public RadioButton rdoOther;
    public Button btnBack1;
    @FXML
    private Button btnNext2;

    @FXML
    private RadioButton rdoFather;

    @FXML
    private RadioButton rdoMother;

    @FXML
    private TextField txtGuardianAddress;

    @FXML
    private TextField txtGuardianEmail;

    @FXML
    private TextField txtGuardianFixedLineNum;

    @FXML
    private TextField txtGuardianMobileNum;

    @FXML
    private TextField txtGuardianName;

    @FXML
    private TextField txtGuardianOccupation;

    private Scene mainScene;        //create field for main scene
    private MainViewController ctrl;


    public void init(Scene mainScene, MainViewController mainViewController) {
        this.mainScene = mainScene;
        this.ctrl = mainViewController;

    }

    @FXML
    void btnNextOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnNext2.getScene().getWindow();

        URL fxmlFile2 = this.getClass().getResource("/view/ThirdView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile2);
        AnchorPane thirdRoot = fxmlLoader.load();

        ThirdViewController thirdViewController = fxmlLoader.getController();
        thirdViewController.init(btnNext2.getScene());

        stage.setScene(new Scene(thirdRoot));
//
//        stage.setScene(new Scene(new FXMLLoader(
//                this.getClass().getResource("/view/ThirdView.fxml")).load()));


//
//        stage.setScene(new Scene(thirdRoot));

    }

    public void btnBack1OnAction(ActionEvent actionEvent) throws IOException {


        ctrl.init(btnBack1.getScene());

        Stage stage = (Stage) btnBack1.getScene().getWindow();
        stage.setScene(mainScene);

    }
}
