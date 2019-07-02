package Controller;

import Model.Messages.Conversation;
import Model.Messages.Mail;

public class currentMail {
    private static Mail mail;
    private static Conversation conversation;

    public static Conversation getConversation() {
        return conversation;
    }

    public static void setConversation(Conversation conversation) {
        currentMail.conversation = conversation;
    }

    public static Mail getMail() {
        return mail;
    }

    public static void setMail(Mail mail) {
        currentMail.mail = mail;
    }
}
