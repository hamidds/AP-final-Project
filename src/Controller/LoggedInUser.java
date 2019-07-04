package Controller;

import Model.Connection.Connection;
import Model.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class LoggedInUser {

    private static User loggedInUser;
    private static Connection loggedInUserConnection;
    private static Image loggedInUserImage;

    public static Image getLoggedInUserImage() {
        return loggedInUserImage;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static Connection getLoggedInUserConnection() {
        return loggedInUserConnection;
    }

    public static void setLoggedInUser(User loggedInUser) throws IOException {
        LoggedInUser.loggedInUser = loggedInUser;
        loggedInUserConnection = new Connection(loggedInUser);
        loggedInUserConnection.initializeServices();
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(loggedInUser.getProImage()));
        loggedInUserImage = SwingFXUtils.toFXImage(img, null);

    }
}