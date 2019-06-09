package Model.Messages;

public enum MessageType
{
    /**
     * connect to a user
     */
    Register,
    /**
     * disconnect user from server
     */
    Disconnect,
    /**
     * send a text message
     */
    Text,
    /**
     * send a message to indicate error
     */
    Error,

    AvailableUsername,
}