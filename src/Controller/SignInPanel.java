package Controller;

import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SignInPanel {


    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    @FXML
    private Button signIn;
    @FXML
    private PasswordField logPassword;
    @FXML
    private TextField logUsername;


    private void connectToServer() throws IOException {
        client = new Socket("localhost", 8888);
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        System.out.println("connected");
    }

    private void disconnectFromServer() throws IOException {
        out.writeObject(new Message(MessageType.Disconnect, "", "", ""));
    }

    public void signInPanel(ActionEvent actionEvent) {
    }

    public void registerPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Register - Panel.fxml");
    }

    public void Exit(ActionEvent actionEvent) {

    }

    public void enter(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        connectToServer();
        Message message = new Message(MessageType.Login, logUsername.getText(), logPassword.getText(), "");
        out.writeObject(message);
        out.flush();
        Message message1 = (Message) in.readObject();
        if (message1.isOkLogin()) {
            System.out.println("hi");
            LogedInUser.setLoggedInUser(message1.getUser());
            new PageLoader().Load("../View/Main - Panel.fxml");
            System.out.println("login successful");
        }
    }
}
