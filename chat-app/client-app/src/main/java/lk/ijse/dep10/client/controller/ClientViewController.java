package lk.ijse.dep10.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.ijse.dep10.shared.Header;
import lk.ijse.dep10.shared.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientViewController {

    @FXML
    private ListView<String> lstUsers;

    @FXML
    private TextField txtMessage;

    @FXML
    private TextArea txtMessageHistory;


    /*User defined*/
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket clientSocket;



    public void initialize() {
        connectToServer();        //connect to the server

        readServerResponse();       //read server responses

        Platform.runLater(this::closeSocket);
    }

    private void closeSocket() {
        txtMessage.getScene().getWindow().setOnCloseRequest(event->{
            try {
                oos.writeObject(new Message(Header.EXIT, null));
                oos.flush();
                if(!clientSocket.isClosed()) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void readServerResponse() {
        new Thread(()->{
            try {
                ois = new ObjectInputStream(clientSocket.getInputStream());

                while (true) {

                    Message msg = (Message) ois.readObject();
                    if (msg.getHeader() == Header.USER) {
                        ArrayList<String> ipAddressList = (ArrayList<String>) msg.getBody();
                        Platform.runLater(()->{
                            lstUsers.getItems().clear();
                            lstUsers.getItems().addAll(ipAddressList);
                        });

                    } else if (msg.getHeader() == Header.MESSAGE) {
                        Platform.runLater(()->{
                            txtMessageHistory.setText(msg.getBody().toString());
                        });
                    }

                }

            } catch (Exception e) {
                if (e instanceof EOFException) {
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.ERROR, "Connection lost, try again!").showAndWait();
                        Platform.exit();
                    });
                } else if (!clientSocket.isClosed()) {
                    e.printStackTrace();
                }


            }

        }).start();


    }

    private void connectToServer() {

        try {
            clientSocket = new Socket("192.168.8.101", 5050);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.flush();


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to connect to the server").showAndWait();
            Platform.exit();
        }

    }

    @FXML
    void txtMessageOnAction(ActionEvent event) {
        try {
            Message newMsg = new Message(Header.MESSAGE, txtMessage.getText());
            oos.writeObject(newMsg);
            oos.flush();
            txtMessage.clear();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to connect to the server, try again").show();
            e.printStackTrace();
        }

    }

}
