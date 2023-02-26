package lk.ijse.dep10.app.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class MainSceneController {

    @FXML
    private Button btnCopy;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSelectFileDestination;

    @FXML
    private Button btnSelectFileSource;

    @FXML
    private Label lblProgressBar;

    @FXML
    private Label lblSelectFileDestination;

    @FXML
    private Label lblSelectFileSource;

    @FXML
    private ProgressBar prg;


    private File sourceFile;
    private File destinationDirectory;
    private File newFile;

    public void initialize() {
        btnReset.fire();

        lblSelectFileDestination.setText("No Such File or Directory");
        lblSelectFileSource.setText("No Such File or Directory");
        btnCopy.setDisable(true);

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        btnCopy.setDisable(true);

        lblSelectFileDestination.setText("No Such File or Directory");
        lblSelectFileSource.setText("No Such File or Directory");

    }

    @FXML
    void btnCopyOnAction(ActionEvent event) throws IOException {

        if(newFile.exists()) {
            Optional<ButtonType> optResult = new Alert(Alert.AlertType.CONFIRMATION,
                    "File already Exists. Do you want to Replace?", ButtonType.YES, ButtonType.NO).showAndWait();

            if(optResult.isEmpty()||optResult.get()==ButtonType.NO){
                return;
            }
        }


        /*progress bar*/
        Task<Void>task=new Task<Void>() {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(newFile);



            @Override
            protected Void call() throws Exception {

                double write=0.0;

                while (true) {
                    byte[] buffer = new byte[1024 * 1024 * 10];
                    int read = fis.read(buffer);



                    if(read==-1)break;

                    fos.write(buffer, 0, read);
                    write+=read;

                    updateProgress(write, sourceFile.length());


                }
                System.out.println("task completed");
                fos.close();
                fis.close();
                return null;

            }
        };
        new Thread(task).start();


        prg.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(workerStateEvent -> {
            prg.progressProperty().unbind();
            new Alert(Alert.AlertType.INFORMATION,"Completed").showAndWait();

            prg.setProgress(0.0);
            btnReset.fire();
            btnSelectFileSource.requestFocus();

        });












    }

    @FXML
    void btnSelectFileDestinationOnAction(ActionEvent event) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Destination to save the file");
        destinationDirectory = directoryChooser.showDialog(btnCopy.getScene().getWindow());


        if (destinationDirectory == null) {
            return;
        }

        newFile = new File(destinationDirectory, sourceFile.getName());




        lblSelectFileDestination.setText(newFile.toString());

//        if (Objects.equals(destinationDirectory, sourceFile.getParentFile().toString())) {
//            lblSelectFileDestination.setText("Same folder selected please select seperated folder");
//            return;
//        }
        btnCopy.setDisable(false);




    }

    @FXML
    void btnSelectFileSourceOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Copy");
        sourceFile = fileChooser.showOpenDialog(btnCopy.getScene().getWindow());


        if (sourceFile == null) {
            return;
        }
        lblSelectFileSource.setText(sourceFile.toString());


    }

}
