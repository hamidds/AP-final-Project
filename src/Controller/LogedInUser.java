package Controller;

import Model.Connection.Connection;
import Model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogedInUser {

    public static User loggedInUser;
    public static Connection loggedInUserConnection;
    public static Map<String, Connection> connections = new HashMap<>();

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static Connection getLoggedInUserConnection() {
        return loggedInUserConnection;
    }

    public static void setLoggedInUser(User loggedInUser) {
        LogedInUser.loggedInUser = loggedInUser;
        loggedInUserConnection = connections.get(loggedInUser.getUsername());
    }

    public static void addConnection(String username, Connection connection) {
        connections.put(username, connection);
    }


}
