package lk.ijse.dep10.app.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class MainSceneController {

    public TextField txtSource;
    public TextField txtDestination;
    public Button btnMove;
    public Button btnDelete;
    public TextField txtDeleteSource;
    public Button btnDeleteBrows;
    private double fileSize = 0.0;
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
    private double write = 0.0;
    private double precentage = 0.0;
    private int numbOfFiles=0;


    private File sourceDirectory;
    private File destinationDirectory;
    private File deleteSourceFile;


    public void initialize() {
        btnReset.fire();

    }


    @FXML
    void btnResetOnAction(ActionEvent event) {
        sourceDirectory = null;           //initalize files to null in the biggining
        destinationDirectory = null;
        deleteSourceFile = null;

        numbOfFiles=0;
        precentage = 0;
        prg.progressProperty().unbind();        //reset progress
        prg.setProgress(0.0);

        lblProgressBar.textProperty().unbind();
        lblProgressBar.setText("Progress 0%");      //reset progress


        txtDeleteSource.setText("No Such File or Directory");
        txtSource.setText("No Such File or Directory");
        txtDestination.setText("No Such File or Directory");
        btnCopy.setDisable(true);
        btnDelete.setDisable(true);
        btnMove.setDisable(true);
        btnCopy.setDisable(true);
        btnSelectFileDestination.setDisable(true);

        txtDestination.setText("No Such File or Directory");
        txtSource.setText("No Such File or Directory");

    }

    @FXML
    void btnSelectFileDestinationOnAction(ActionEvent event) throws IOException {
        /*create directory chooser for destination*/
        DirectoryChooser desChooser = new DirectoryChooser();
        desChooser.setTitle("Select Copy Directory");
        destinationDirectory = desChooser.showDialog(btnCopy.getScene().getWindow());
        if (!destinationDirectory.exists()) return;

        txtDestination.setText(destinationDirectory.toString());
        btnCopy.setDisable(false);
        btnMove.setDisable(false);
        btnDelete.setDisable(false);


    }

    @FXML
    void btnSelectFileSourceOnAction(ActionEvent event) throws IOException {
        JFileChooser sourceChooser = new JFileChooser();
        sourceChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        sourceChooser.setDialogTitle("Select file or Directory to copy");
        sourceChooser.showOpenDialog(null);
        sourceDirectory = sourceChooser.getSelectedFile();


        if (sourceDirectory == null) return;
        txtSource.setText(sourceDirectory.toString());

        determineSourceFileSize(sourceDirectory);

        System.out.println("File sizes " + fileSize);


        btnSelectFileDestination.setDisable(false);
        btnDelete.setDisable(false);


    }

    @FXML
    void btnCopyOnAction(ActionEvent event) throws IOException {
        if ((new File(destinationDirectory, sourceDirectory.getName())).exists()) {
            Optional<ButtonType> optionalButtonType = new Alert(Alert.
                    AlertType.CONFIRMATION, "File already exists. Do you wanna to replace it?",
                    ButtonType.NO, ButtonType.YES).showAndWait();
            if ((optionalButtonType.get() == ButtonType.NO) || optionalButtonType.isEmpty()) {
                btnReset.fire();
                return;
            }
        }

        ((Stage) btnCopy.getScene().getWindow()).setHeight(600);


        dirCopy(sourceDirectory, destinationDirectory);

        prg.progressProperty().unbind();
        prg.setProgress(fileSize);
        lblProgressBar.textProperty().unbind();
        lblProgressBar.setText("100%");
        new Alert(Alert.AlertType.INFORMATION, "All Files copied!").showAndWait();

        btnCopy.getScene().getWindow().setHeight(400);

        btnReset.fire();


    }

    public void btnMoveOnAction(ActionEvent actionEvent) {
        if ((new File(destinationDirectory, sourceDirectory.getName())).exists()) {
            Optional<ButtonType> optional = new Alert(Alert.AlertType.CONFIRMATION,
                    "File Already exits do you want to replace it?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (optional.isEmpty() || optional.get() == ButtonType.NO) return;

        }
        sourceDirectory.renameTo(new File(destinationDirectory, sourceDirectory.getName()));

        new Alert(Alert.AlertType.INFORMATION, "File Moved!").show();


    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        if (deleteSourceFile == null) return;
        Optional<ButtonType> optional = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to delete selected file?",
                ButtonType.YES, ButtonType.NO).showAndWait();
        if ((optional.isEmpty()) || optional.get() == ButtonType.NO) {
            return;
        }

        dirDelete(deleteSourceFile);

        new Alert(Alert.AlertType.INFORMATION, "Selected File Deleted!").show();
        btnReset.fire();


    }


    /*methods related to copy*/

    void dirCopy(File source, File destination) throws IOException {


        if (source.isDirectory()) {
            File newFile = new File(destination, source.getName());
            if (!newFile.exists()) {
                newFile.mkdir();
            }
            File[] filesInSource = source.listFiles();
            for (File file : filesInSource) {
                dirCopy(file, newFile);

            }
        } else {
            fileCopy(source, destination);
        }
    }

    void fileCopy(File source, File destination) throws IOException {       //if file copy using this method

        Task<Void>task=new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                File copiedFile = new File(destination, source.getName());
                FileInputStream fis = new FileInputStream(source);
                FileOutputStream fos = new FileOutputStream(copiedFile);


                while (true) {
                    byte[] buffer = new byte[1024];
                    int read = fis.read(buffer);
                    if (read == -1) break;
                    fos.write(buffer, 0, read);

                    write += read;
                    precentage=(write*100/fileSize);
                    System.out.println("Precentage: "+precentage);


                    updateProgress(write, fileSize);
                    updateMessage(String.format("%s%s", precentage, "%"));


                    System.out.println("Write: " + write);



                }

                fis.close();
                fos.flush();        //flush before close
                fos.close();
                return null;
            }
        };

        new Thread(task).start();
        prg.progressProperty().bind(task.progressProperty());
        lblProgressBar.textProperty().bind(task.messageProperty());


    }


    /*methods related to delete*/

    void dirDelete(File source) {
        if (source.isDirectory()) {
            File[] sourceFiles = source.listFiles();
            for (File file : sourceFiles) {
                dirDelete(file);

            }
            source.delete();

        } else {
            source.delete();
        }
    }

    public void btnDeleteBrowsOnAction(ActionEvent actionEvent) {

        JFileChooser sourceChooser = new JFileChooser();
        sourceChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        sourceChooser.setDialogTitle("Select file or Directory to delete");
        sourceChooser.showOpenDialog(null);
        deleteSourceFile = sourceChooser.getSelectedFile();
        if (deleteSourceFile == null) {
            return;
        }
        btnDelete.setDisable(false);
        txtDeleteSource.setText(deleteSourceFile.toString());

    }

    private void determineSourceFileSize(File sourceDirectory) {
        if (sourceDirectory.isDirectory()) {
            File[] fileList = sourceDirectory.listFiles();
            for (File file : fileList) {
                determineSourceFileSize(file);
            }
        } else {
            fileSize += sourceDirectory.length();
            numbOfFiles+=1;
        }

    }
}
