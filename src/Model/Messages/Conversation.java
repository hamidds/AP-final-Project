package Model.Messages;

import Model.User;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private List<Mail> mails = new ArrayList<>();
    private Mail lastMail;
    private User sender;
    private User receiver;

    private boolean bookMarked;
    private boolean read;
    private boolean inTrash;


    public void setBookMarked(boolean bookMarked) {
        this.bookMarked = bookMarked;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isBookMarked() {
        return bookMarked;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isInTrash() {
        return inTrash;
    }

    public void setInTrash(boolean inTrash) {
        this.inTrash = inTrash;
    }

    public Conversation(Mail mail, User sender, User receiver) {
        mails.add(mail);
        lastMail = mail;
        this.sender = sender;
        this.receiver = receiver;
    }

    public List<Mail> getMails() {
        return mails;
    }

    public Mail getLastMail() {
        return lastMail;
    }

    public void addMail(Mail mail) {
        mails.add(mail);
        lastMail = mail;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }
// date and time
}
