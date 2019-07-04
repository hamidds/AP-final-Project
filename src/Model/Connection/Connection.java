package Model.Connection;

import Controller.IPSetter;
import Model.User;
import Model.Messages.Message;
import Model.Messages.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Connection {


    private String currentUsername;
    private User currentUser;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isClient;

    public Connection(User currentUser) {
        this.currentUser = currentUser;
        this.currentUsername = currentUser.getUsername();
        try {
            client = new Socket(IPSetter.getIP(), 8888);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            Message request = new Message(MessageType.Connect, currentUsername, "", "");
            request.setUser(currentUser);
            sendRequest(request);
        } catch (IOException e) {
            throw new ServerConnectionException();
        }
    }

    public Connection(User currentUser, boolean isClient) {
        this.currentUser = currentUser;
        this.currentUsername = currentUser.getUsername();
        this.isClient = isClient;
        try {
            client = new Socket(IPSetter.getIP(), 8888);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            Message request = new Message(isClient ? MessageType.Register : MessageType.Connect, currentUsername, "", "");
            request.setUser(currentUser);
            sendRequest(request);
        } catch (IOException e) {
            throw new ServerConnectionException();
        }
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void disconnect() {
        sendRequest(new Message(MessageType.Disconnect, currentUsername, "", ""));
    }

    public void initializeServices() {
        Thread listenerThread = new Thread(new ListenerService(this), "Listener Thread");
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public void sendRequest(Message request) {
        try {
            out.writeObject(request);
        } catch (IOException e) {
            throw new ServerConnectionException();
        }
    }

    public void sendText(String textMessage, String receiver) {
        sendRequest(new Message(MessageType.Text, currentUsername, receiver, textMessage));
    }

    public String getRespond() {
        try {
            return ClientMessageHandler.handle((Message) in.readObject(), currentUser);
        } catch (IOException | ClassNotFoundException e) {
            throw new ServerConnectionException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(currentUser, that.currentUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentUser);
    }


}

