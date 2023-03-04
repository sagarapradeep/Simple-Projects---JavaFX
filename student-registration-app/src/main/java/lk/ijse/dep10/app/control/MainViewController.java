package lk.ijse.dep10.app.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep10.app.enums.Sex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;

public class MainViewController {

    public Button btnNext;
    public Button btnNewStudent;
    public Button btnTempSave;
    @FXML
    private ImageView imgProfilePic;

    @FXML
    private Label lblTitle;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFixedLineNum;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMobileNum;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRace;
    private Scene secondScene;
    private boolean isSecondVieEntered = false;

    private File desktopFile = new File(new File(System.getProperty("user.home")), "Desktop");
    private File saveFile = new File(desktopFile, "student.ijse");

    public void initialize() {
        try {
            if (!saveFile.exists()) {
                System.out.println("FIle no in");
                return;
            }
//            FileInputStream fis = new FileInputStream(saveFile);
////            BufferedInputStream bis = new BufferedInputStream(fis);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            Student student = (Student) ois.readObject();
//            Sex sex = student.getSex();
//
//
//            System.out.println(sex.name());

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the student!").show();
            btnNewStudent.fire();
        }

    }

    public void init(Scene scene) {
        System.out.println("init called");
        this.secondScene = scene;
        System.out.println(secondScene);

    }

    public void btnNextOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnNext.getScene().getWindow();
        System.out.println(secondScene);

        if (!isSecondVieEntered) {

            URL fxmlFile = getClass().getResource("/view/SecondView.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
            AnchorPane secondRoot = fxmlLoader.load();

            SecondViewController secondViewController = fxmlLoader.getController();
            secondViewController.init(btnNext.getScene());
            isSecondVieEntered = true;

            stage.setScene(new Scene(secondRoot));
        }else {
            System.out.println("Secod");

            stage.setScene(secondScene);
            System.out.println("Finished");

        }


    }

    public void btnNewStudentOnAction(ActionEvent actionEvent) {
    }

    public void btnTempSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        Sex sex = rdoFemale.isSelected() ? Sex.FEMALE : Sex.MALE;
        String race = txtRace.getText();
        String mobileNumber = txtMobileNum.getText();
        String fixedNumber = txtFixedLineNum.getText();
        String email = txtEmail.getText();

//        Student newStudent = new Student(id, name, address, sex, race, mobileNumber, fixedNumber, email);

        try {

            FileOutputStream fos = new FileOutputStream(saveFile);
//            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(newStudent);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save the student!").show();
            btnNewStudent.fire();
        }
    }


}
