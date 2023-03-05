package lk.ijse.dep10.server;

import javafx.scene.control.Alert;
import lk.ijse.dep10.server.util.User;
import lk.ijse.dep10.shared.Header;
import lk.ijse.dep10.shared.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AppInitializer {

    private static volatile ArrayList<User> userArrayList = new ArrayList<>();
    private static volatile String chatHistory = "";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5050);
        System.out.println("Server is listening");


        while (true) {
            System.out.println("Waiting for new connection!");
            Socket localSocket = serverSocket.accept();
            System.out.println("new User Came!");
            User user = new User(localSocket);
            userArrayList.add(user);
            System.out.println("New User " + localSocket.getRemoteSocketAddress() + " joined!");

            new Thread(() -> {


                try {
                    sendChatHistoryToNewLogger(user);       //send chat history to newly logged user

                    broadCastUsers();                   //broadcast user list

                    ObjectInputStream ois = user.getObjectInputStream();

                    while (true) {
                        Message msg = (Message) ois.readObject();
                        if (msg.getHeader() == Header.MESSAGE) {
                            chatHistory += String.format("%s :%s%s", user.getRemoteIpAddress(), msg.getBody(),"\n");
                            broadCastChatHistory();             //broadcast chat history
                        } else if (msg.getHeader() == Header.EXIT) {
                            removeUser(user);
                            return;
                        }
                    }


                } catch (Exception e) {
                    removeUser(user);
                    if (e instanceof EOFException) return;

                    e.printStackTrace();
                }

            }).start();


        }

    }

    private static void removeUser(User user) {
        if (userArrayList.contains(user)) {
            userArrayList.remove(user);
            broadCastUsers();

            if (!user.getLocalSocket().isClosed()) {
                try {
                    user.getLocalSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void broadCastChatHistory() {
        for (User user : userArrayList) {
            new Thread(()->{
                try {
                    ObjectOutputStream oos = user.getObjectOutputStream();
                    oos.writeObject(new Message(Header.MESSAGE, chatHistory));
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }

    }

    private static void broadCastUsers() {
        System.out.println("Braodcast users");

        ArrayList<String> ipAddressList = new ArrayList<>();
        for (User user : userArrayList) {
            ipAddressList.add(user.getRemoteIpAddress());
        }


        for (User user : userArrayList) {

            new Thread(()->{
                try {
                    ObjectOutputStream oos = user.getObjectOutputStream();
                    oos.writeObject(new Message(Header.USER, ipAddressList));
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("failed to send the user list");
                }

            }).start();



        }


    }

    private static void sendChatHistoryToNewLogger(User user) {
        ObjectOutputStream oos = user.getObjectOutputStream();

        try {
            oos.writeObject(new Message(Header.MESSAGE, chatHistory));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to send the chat history to newly logged user");
        }
        System.out.println("Chat history");

    }

}
