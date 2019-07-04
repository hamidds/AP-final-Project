package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * we use this Class to set our Server's IP
 * and then use it when needed
 */
public class IPSetter {

    private static String IP;
    private static String PORT;

    @FXML
    private TextField IPtext, Port;

    public static String getIP() {
        return IP;
    }

    public static String getPORT() {
        return PORT;
    }

    private static void setIP(String IP) {
        IPSetter.IP = IP;
    }

    private static void setPORT(String PORT) {
        IPSetter.PORT = PORT;
    }

    public void signInPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/SignIn - Panel.fxml");
    }

    public void registerPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Register - Panel.fxml");

    }

    public void Exit(ActionEvent actionEvent) {

    }

    public void SetIP(ActionEvent actionEvent) {
        setIP(IPtext.getText());
        setPORT(Port.getText());
    }
}
