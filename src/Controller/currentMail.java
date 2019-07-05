package Controller;

import Model.Messages.Conversation;
import Model.Messages.Mail;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * this class is used when we want to
 * open an Mail and see its details
 */
public class currentMail {
    private static Mail mail;
    private static Conversation conversation;
    private static Image senderImage;

    public static Conversation getConversation() {
        return conversation;
    }

    public static Image getSenderImage() {
        return senderImage;
    }

    public static void setConversation(Conversation conversation) {
        currentMail.conversation = conversation;
    }

    public static Mail getMail() {
        return mail;
    }

    public static void setMail(Mail mail) throws IOException {
        currentMail.mail = mail;
        if (mail.isFromGoogleDelivery()) {
            senderImage = new Image("View/Resources/GoogleDelivery1.png");
        } else {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(mail.getSenderUser().getProImage()));
            senderImage = SwingFXUtils.toFXImage(img, null);
        }
    }
}
