package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ComposePanel implements Initializable {
    @FXML
    private TextField recepient , subject , mailText ;
    @FXML
    private Button send;

    public void inbox(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Main - Panel.fxml");
    }

    public void compose(ActionEvent actionEvent) {

    }

    public void registerPanel(ActionEvent actionEvent) {

    }

    public void Exit(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {

    }

    public void send(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("hi");
    }
}
