package lk.ijse.dep10.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.ijse.dep10.shared.Header;
import lk.ijse.dep10.shared.Message;

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
                e.printStackTrace();
                System.out.println("Connection lost");

            }

        }).start();


    }

    private void connectToServer() {

        try {
            clientSocket = new Socket("192.168.8.101", 5050);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.flush();


        } catch (Exception e) {
            System.out.println("Error when connecting");
            e.printStackTrace();
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
            System.out.println("Filed to send the message");
            e.printStackTrace();
        }

    }

}
