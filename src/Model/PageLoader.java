package Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;

public class PageLoader {
    private static final int WIDTH = 930;
    private static final int HEIGHT = 600;
    private static Stage stage;

    public static void initStage(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Log in");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
    }

    public void Load(String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public void Load(String url, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
        fxmlLoader.setController(controller);
        fxmlLoader.load();
    }
}
