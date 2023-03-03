package lk.ijse.dep10.app.controller;



import com.itextpdf.html2pdf.HtmlConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

public class EditorSceneController {

    public boolean condition;

    public AnchorPane root;
    public MenuItem mnSaveAs;
    @FXML
    private MenuItem mnAbout;

    @FXML
    private MenuItem mnClose;

    @FXML
    private MenuItem mnNew;

    @FXML
    private MenuItem mnOpen;

    @FXML
    private MenuItem mnPrint;

    @FXML
    private MenuItem mnSave;

    @FXML
    private HTMLEditor txtEditor;

    private boolean isSave=false;
    private File file=null;
    private String initTxt="<html dir=\"ltr\"><head></head><body contenteditable=\"true\"></body></html>";

    private String stageTitle=null;




    public void initialize() {

        txtEditor.requestFocus();

    }

    public void initData(Stage stage) {

        stage.setOnCloseRequest(windowEvent -> {
            try {
                condition = isNotEdited();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (condition)return;
            Optional<ButtonType> optButtons = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you want to save before exit", ButtonType.YES, ButtonType.NO,ButtonType.CANCEL).showAndWait();
            if(optButtons.isEmpty())return;
            if (optButtons.get() == ButtonType.NO) {
                stage.close();
                return;
            }
            if (optButtons.get() == ButtonType.CANCEL) {
                windowEvent.consume();
                return;

            }

            ActionEvent event = new ActionEvent();

            try {
                mnSaveOnAction(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    void mnAboutOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Feather Pad");

        URL fxmlFile = this.getClass().getResource("/view/AboutScene.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setWidth(600);
        stage.setHeight(450);
        stage.setResizable(false);

        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    void mnCloseOnAction(ActionEvent event) throws IOException {


        if(isNotEdited()){
            Stage stage = (Stage) txtEditor.getScene().getWindow();
            stage.close();
            return;
        }
        Optional<ButtonType> optButton=new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to save previous text?",ButtonType.YES,ButtonType.NO).showAndWait();
        if(optButton.isEmpty())return;
        if (optButton.get() == ButtonType.NO) {
            Stage currentstage=(Stage)txtEditor.getScene().getWindow();
            currentstage.close();
            return;
        }
        ActionEvent event1 = new ActionEvent();
        mnSaveOnAction(event1);

    }

    @FXML
    void mnNewOnAction(ActionEvent event) throws IOException {

        if(isNotEdited()){
            file=null;
            Stage stage = (Stage) txtEditor.getScene().getWindow();
            stageTitle="Untiled Document";
            stage.setTitle("*" + stageTitle);
            txtEditor.setHtmlText("");
            return;
        }


        Optional<ButtonType> optButton=new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to save previous text?",ButtonType.YES,ButtonType.NO).showAndWait();

        if(optButton.isEmpty())return;
        if (optButton.get() == ButtonType.NO) {
            Stage stage = (Stage) txtEditor.getScene().getWindow();
            stageTitle = "Untitled Document";
            stage.setTitle("*" + stageTitle);
            isSave=false;
            txtEditor.setHtmlText("");

        }else {
            ActionEvent event1 = new ActionEvent();
            mnSaveOnAction(event1);

//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Save a text file");
//            file = fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
//            if (file == null) {
//                return;
//            }
//
//            FileOutputStream fos = new FileOutputStream(file, false);
//            String text = txtEditor.getHtmlText();
//            byte[] bytes = text.getBytes();
//            fos.write(bytes);
//            fos.close();
//            txtEditor.setHtmlText("");
        }

    }

    @FXML
    void mnOpenOnAction(ActionEvent event) throws IOException {
        isSave=true;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a text file");
        file = fileChooser.showOpenDialog(txtEditor.getScene().getWindow());



        if (file == null) {
            return;
        }

        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        txtEditor.setHtmlText(new String(bytes));
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        stageTitle=file.getName();
        stage.setTitle(stageTitle);

    }

    @FXML
    void mnPrintOnAction(ActionEvent event) throws IOException {
        if(txtEditor==null){
            System.out.println("null");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file to save the pdf file");
        File fileLocation=fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
        if(fileLocation==null)return;


        OutputStream os = new FileOutputStream(fileLocation);
        HtmlConverter.convertToPdf(txtEditor.getHtmlText(), os);



    }

    @FXML
    void mnSaveOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        if(isSave){
            FileOutputStream fos = new FileOutputStream(file, false);
            String text = txtEditor.getHtmlText();
            byte[] bytes = text.getBytes();
            fos.write(bytes);
            fos.close();
            isSave=true;
            stageTitle = file.getName();
            stage.setTitle(stageTitle);
            condition=false;
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save a text file");
        file = fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
        if (file == null) {
            return;
        }

        FileOutputStream fos = new FileOutputStream(file, false);
        String text = txtEditor.getHtmlText();
        byte[] bytes = text.getBytes();
        fos.write(bytes);
        fos.close();
        isSave=true;
        condition=false;
        stageTitle=file.getName();
        stage.setTitle(stageTitle);

    }

    public void rootOnDragOver(DragEvent dragEvent) {

        dragEvent.acceptTransferModes(TransferMode.ANY);        //get drag files and accept
        

    }

    public void rootOnDragDropped(DragEvent dragEvent) throws IOException {
        isSave=true;

        file = dragEvent.getDragboard().getFiles().get(0);      //take fist file of selected file set
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        stageTitle = file.getName();
        stage.setTitle(stageTitle);


        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = fis.readAllBytes();
        fis.close();
        txtEditor.setHtmlText(new String(bytes));
        isSave=true;
    }


    public void mnSaveAsOnAction(ActionEvent actionEvent) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save a text file");
        file = fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
        if (file == null) {
            return;
        }

        FileOutputStream fos = new FileOutputStream(file, false);
        String text = txtEditor.getHtmlText();
        byte[] bytes = text.getBytes();
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        stageTitle = file.getName();
        stage.setTitle(stageTitle);

        isSave=true;

        fos.write(bytes);
        fos.close();
    }


    public boolean isNotEdited() throws IOException {
        String currentText = txtEditor.getHtmlText();


        if((file==null)&&(currentText.equals(initTxt))) return true;
        if(file==null)return false;

        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        String previousText = new String(bytes);
        return previousText.equals(currentText);
    }

    public void txtEditorOnKeyReleased(KeyEvent keyEvent) throws IOException {
        if(isNotEdited()) return;
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        if (file == null) {
            stageTitle = "Untitled Document";
        }
        else {
            stageTitle = file.getName();

        }
        stage.setTitle("*" + stageTitle);

    }
}
