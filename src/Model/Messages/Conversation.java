package Model.Messages;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private List<Mail> mails = new ArrayList<>();

    public List<Mail> getMails() {
        return mails;
    }

    public void addMail(Mail mail) {
        mails.add(mail);
    }

    // date and time
    // user which starts it
    // receiver user
}
