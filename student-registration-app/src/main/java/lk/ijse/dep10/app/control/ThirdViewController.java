package lk.ijse.dep10.app.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class ThirdViewController {


    public Button btnBck2;
    @FXML
    private Button btnSave;

    @FXML
    private Button btnSaveModules;

    @FXML
    private ComboBox<?> cmbDepartment;

    @FXML
    private ListView<?> lstAllModules;

    @FXML
    private ListView<?> lstSelectedModules;

    @FXML
    private RadioButton rdoFullTime;

    @FXML
    private RadioButton rdoPartTime;
    private Scene secondScene;

    public void init(Scene secondScene) {
        this.secondScene = secondScene;

    }

    @FXML
    void btnSaveModulesOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    public void btnBck2OnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBck2.getScene().getWindow();
        stage.setScene(secondScene);
    }
}
