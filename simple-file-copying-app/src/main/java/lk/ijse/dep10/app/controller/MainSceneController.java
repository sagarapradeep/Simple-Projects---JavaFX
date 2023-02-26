package lk.ijse.dep10.app.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.Optional;

public class MainSceneController {

    public TextField txtSource;
    public TextField txtDestination;
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
    private ProgressBar prg;


    private File sourceFile;
    private File destinationDirectory;
    private File newFile;

    public void initialize() {
        btnReset.fire();

        txtSource.setText("No Such File or Directory");
        txtDestination.setText("No Such File or Directory");
        btnCopy.setDisable(true);

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        btnCopy.setDisable(true);
//        btnReset.getScene().getWindow().setHeight(400);


        txtDestination.setText("No Such File or Directory");
        txtSource.setText("No Such File or Directory");

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
        btnCopy.getScene().getWindow().setHeight(473);


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
                    int progress =(int) (write * 100 / sourceFile.length());

                    updateProgress(write, sourceFile.length());
                    updateMessage(String.format("%d%s", progress, "%"));


                }

                fos.close();
                fis.close();
                return null;

            }
        };
        new Thread(task).start();


        prg.progressProperty().bind(task.progressProperty());
        lblProgressBar.textProperty().bind(task.messageProperty());

        task.setOnSucceeded(workerStateEvent -> {
            prg.progressProperty().unbind();
            new Alert(Alert.AlertType.INFORMATION,"Completed").showAndWait();

            prg.setProgress(0.0);
            btnCopy.getScene().getWindow().setHeight(400);
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




        txtDestination.setText(newFile.toString());


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
        txtSource.setText(sourceFile.toString());


    }

}
