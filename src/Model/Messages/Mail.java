package Model.Messages;

public class Mail extends Message {
    private String subjet;
    // date and time
    public Mail(MessageType messageType, String sender, String receiver, String messageText) {
        super(messageType, sender, receiver, messageText);
    }
}
