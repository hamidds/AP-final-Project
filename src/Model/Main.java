package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("../View/Main - Panel.fxml"));
        primaryStage.setTitle("G-mail");
        primaryStage.setScene(new Scene(root, 930, 575));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
