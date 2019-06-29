package Model.Messages;

import Model.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mail implements Serializable {
    private String sender;
    private String receiver;
    private String subject;
    private User receiverUser;
    private User senderUser;

    public User getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(User receiverUser) {
        this.receiverUser = receiverUser;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    private String text;
    // date and time
    private LocalDateTime time;


    public Mail(LocalDateTime time, String sender, String receiver, String subject, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.text = text;
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getTimeString() {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm a");
        return dtf.format(time);
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
