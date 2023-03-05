package lk.ijse.dep10.server.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class User {
    private Socket localSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public User() {
    }

    public User(Socket userSocket) throws IOException {
        this.localSocket = userSocket;

        objectOutputStream = new ObjectOutputStream(localSocket.getOutputStream());
        objectOutputStream.flush();

    }

    public Socket getLocalSocket() {
        return localSocket;
    }

    public ObjectInputStream getObjectInputStream() throws IOException {
        return objectInputStream!=null?objectInputStream:
                (objectInputStream=new ObjectInputStream(localSocket.getInputStream()));
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setLocalSocket(Socket localSocket) {
        this.localSocket = localSocket;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public String getRemoteIpAddress() {
        return ((InetSocketAddress)(localSocket.getRemoteSocketAddress())).getHostString();
    }
}
