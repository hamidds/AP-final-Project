package Controller;

import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword implements Initializable {

    @FXML
    private AnchorPane first, second;
    @FXML
    private ComboBox<String> SQ;
    @FXML
    private PasswordField nP, nC;
    @FXML
    private TextField SQA, user;

    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SQ.getItems().add("Where was your mother born ?");
        SQ.getItems().add("What is the name of your childhood best friend ?");
        SQ.getItems().add("what is the name of your favorite soccer player ?");
    }

    private void connectToServer() throws IOException {
        client = new Socket(IPSetter.getIP(), 8888);
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
//        System.out.println("connected");
//        User Controller = new User(null, null, "controller", null);
//        connection = new Connection(Controller, false);
//        out = connection.getOut();
//        in = connection.getIn();
    }

    private void disconnectFromServer() throws IOException {
//        connection.sendRequest(new Message(MessageType.Disconnect, "", "", ""));
        out.writeObject(new Message(MessageType.Disconnect, "", "", ""));
    }


    public void signInPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/SignIn - Panel.fxml");
    }

    public void registerPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Register - Panel.fxml");
    }

    public void Exit(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/IPSetter.fxml");
    }

    public void nextP(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        connectToServer();
        Message CheckSecurityQ = new Message(MessageType.CheckSecurityQuestion, user.getText(), SQ.getValue(), SQA.getText());
        out.writeObject(CheckSecurityQ);
        out.flush();
        if (in.readBoolean()) {
            first.setVisible(false);
//            TranslateTransition transition1 = new TranslateTransition(Duration.millis(1000), first);
//            transition1.setToX(-550);
//            transition1.playFromStart();
            TranslateTransition transition2 = new TranslateTransition(Duration.millis(1000), second);
            transition2.setToX(-500);
            transition2.playFromStart();

        }
        disconnectFromServer();
    }

    public void changePass(ActionEvent actionEvent) throws IOException {
        if (passwordChecker()) {
            connectToServer();
            if (nP.getText().equals(nC.getText())) {
                Message changePass = new Message(MessageType.ForgotPass, nP.getText(), "", user.getText());
                out.writeObject(changePass);
            }
            disconnectFromServer();
            Alert changedPass = new Alert(Alert.AlertType.INFORMATION, "Your Password has been changed!");
            changedPass.showAndWait();
        } else {
            Alert invalidPass = new Alert(Alert.AlertType.INFORMATION, "Please use 8 or more characters with a mix of letters, numbers & symbols");
            invalidPass.showAndWait();
        }
    }

    private boolean passwordChecker() {
        String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(nP.getText());
        return matcher.matches();
    }
}
