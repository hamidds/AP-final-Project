package Model;

import Controller.IPSetter;
import Model.Messages.Message;
import Model.Messages.MessageType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application {

    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    @Override
    public void start(Stage primaryStage) throws Exception {
        PageLoader.initStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("../View/Register - Panel.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("../View/Main - Panel.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("../View/Settings - Panel.fxml"));
        primaryStage.setTitle("G-mail");
        primaryStage.setScene(new Scene(root, 930, 575));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void connectToServer() throws IOException {
        client = new Socket(IPSetter.getIP(), 8888);
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
    }

    private void disconnectFromServer() throws IOException {
        out.writeObject(new Message(MessageType.Disconnect, "", "", ""));
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        connectToServer();
        out.writeObject(new Message(MessageType.SaveUsers,null,null,null));
        disconnectFromServer();
        System.out.println("done");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
