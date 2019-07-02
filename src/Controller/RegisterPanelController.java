package Controller;

import Model.Connection.Connection;
import Model.Gender;
import Model.Messages.Message;
import Model.Messages.MessageType;
import Model.PageLoader;
import Model.User;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPanelController implements Initializable {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private File selectedFile;


    @FXML
    private TextField firstname, lastname, username, visiblePassword, visibleConfirm, phoneNumber;
    @FXML
    private Button next;
    @FXML
    private PasswordField confirm, password;
    @FXML
    private AnchorPane anchor, nextAnchor, nextSteps;
    @FXML
    private DatePicker birthDate;
    @FXML
    private Label passwordWarning, gmail, usernameWarning, createYourAccount;
    @FXML
    private FontIcon eye, eyeSlash;
    @FXML
    private FontIcon W1, W2, W3, W4, W5, W6;
    @FXML
    private ImageView image;
    @FXML
    private RadioButton male, female;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private boolean passwordChecker() {
        Pattern pattern;
        Matcher matcher;
        String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";
        pattern = Pattern.compile(PASSWORD_REGEX);
        matcher = pattern.matcher(password.getText());
        if (!matcher.matches())
            passwordWarnings();
        return matcher.matches();
    }

    public void next(ActionEvent actionEvent) throws IOException {
        nextSteps.setVisible(true);
        connectToServer();
        if (passwordChecker() && birthDateChecker() && emptyFieldChecker()) {
            if (password.getText().equals(confirm.getText()) || visiblePassword.getText().equals(visibleConfirm.getText())) {
                out.writeObject(new Message(MessageType.AvailableUsername, username.getText(), "", ""));
                if (in.readBoolean()) {
                    TranslateTransition transition1 = new TranslateTransition(Duration.millis(1000), nextAnchor);
                    TranslateTransition transition2 = new TranslateTransition(Duration.millis(1000), nextSteps);
                    transition2.setToX(-500);
                    transition2.playFromStart();
                    transition1.setToX(-550);
                    transition1.playFromStart();
                    disconnectFromServer();
                } else {
                    Alert userNotFound = new Alert(Alert.AlertType.INFORMATION, "this username is already taken!");
                    userNotFound.showAndWait();
                }
            } else {
                passwordWarning.setText("Those passwords didn't match. Try again.");
                passwordWarnings();
            }
        } else {
//            passwordWarnings();
        }

    }

    private void connectToServer() throws IOException {
        client = new Socket(IPSetter.getIP(), 8888);
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        System.out.println("connected");
    }

    private void disconnectFromServer() throws IOException {
        out.writeObject(new Message(MessageType.Disconnect, "", "", ""));
    }

    private boolean birthDateChecker() {
        String birthDate = this.birthDate.getEditor().getText();
        if (Integer.valueOf(birthDate.split("/")[2]) > 2006) {
            W6.setVisible(true);
            this.birthDate.setStyle("-fx-border-color: #D93025");
            return false;
        }
        return true;
    }

    private void passwordWarnings() {
        passwordWarning.setStyle("-fx-text-fill: #D93025");
        password.setStyle("-fx-border-color: #D93025");
        confirm.setStyle("-fx-border-color: #D93025");
        visibleConfirm.setStyle("-fx-border-color: #D93025");
        visiblePassword.setStyle("-fx-border-color: #D93025");
        W4.setVisible(true);
        W5.setVisible(true);
    }

    private boolean emptyFieldChecker() {
        List<TextField> fields = new ArrayList<>(Arrays.asList(firstname, lastname, username, password, confirm));
        List<FontIcon> warnings = new ArrayList<>(Arrays.asList(W1, W2, W3, W4, W5, W6));
        int counter = -1;
        for (TextField field : fields) {
            counter++;
            if (field.getText().isEmpty()) {
                field.setStyle("-fx-border-color: #D93025; -fx-prompt-text-fill: #D93025");
                warnings.get(counter).setVisible(true);
                return false;
            }
        }
        return true;
    }

    public void invisiblePassword(MouseEvent mouseEvent) {
        changeVisible(eye, eyeSlash, password, confirm, visiblePassword, visibleConfirm);
    }

    public void visiblePassword(MouseEvent mouseEvent) {
        changeVisible(eyeSlash, eye, visiblePassword, visibleConfirm, password, confirm);
    }

    /**
     * changes the visibility of password field
     *
     * @param eyeSlash        font icon
     * @param eye
     * @param visiblePassword
     * @param visibleConfirm
     * @param password
     * @param confirm
     */
    private void changeVisible(FontIcon eyeSlash, FontIcon eye, TextField visiblePassword, TextField visibleConfirm, TextField password, TextField confirm) {
        eyeSlash.setVisible(false);
        eye.setVisible(true);
        visiblePassword.setVisible(false);
        visibleConfirm.setVisible(false);
        password.setText(visiblePassword.getText());
        confirm.setText(visibleConfirm.getText());
        password.setVisible(true);
        confirm.setVisible(true);
    }


    public void Exit(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/IPSetter.fxml");
    }

    public void choose(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image1 = new Image(selectedFile.toURI().toString());
            Circle clip = new Circle(60, 60, 60);
            image.setClip(clip);
            image.setImage(image1);
        }
    }

    public void malePic(MouseEvent mouseEvent) {
        if (selectedFile == null) {
            selectedFile = new File("/View/Resources/boy.png");
            Image boyDefaultImage = new Image("/View/Resources/boy.png");
            image.setImage(boyDefaultImage);
        }
    }

    public void femalePic(MouseEvent mouseEvent) {
        if (selectedFile == null) {
            selectedFile = new File("/View/Resources/girl.png");
            Image girDefaultImage = new Image("/View/Resources/girl.png");
            image.setImage(girDefaultImage);
        }
    }

    public void register(ActionEvent actionEvent) throws IOException {
        // we should initialize user here
        User current = new User(firstname.getText(), lastname.getText(), username.getText(), password.getText());
        current.complete(selectedFile, phoneNumber.getText(), male.isSelected() ? Gender.Male : Gender.Female);
        Connection newUser = new Connection(current);
        newUser.initializeServices();
        LogedInUser.addConnection(current.getUsername(), newUser);
    }

    public void signInPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/SignIn - Panel.fxml");
    }

    public void registerPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().Load("../View/Register - Panel.fxml");

    }

}


