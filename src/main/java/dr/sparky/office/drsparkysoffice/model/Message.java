package dr.sparky.office.drsparkysoffice.model;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a message sent between users.
 */
public class Message implements Serializable {

    private UserAccount sender;
    private UserAccount receiver;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;

    /**
     * Constructor to create a new message.
     * @param sender The user who sent the message
     * @param receiver The user who will receive the message
     * @param message The content of the message
     */
    public Message(UserAccount sender, UserAccount receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Gets the sender of the message.
     * @return The sender of the message
     */
    public UserAccount getSender() {
        return sender;
    }

    /**
     * Sets the sender of the message.
     * @param sender The sender of the message
     */
    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    /**
     * Gets the receiver of the message.
     * @return The receiver of the message
     */
    public UserAccount getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver of the message.
     * @param receiver The receiver of the message
     */
    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }

    /**
     * Gets the content of the message.
     * @return The content of the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the content of the message.
     * @param message The content of the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Checks if the message has been read.
     * @return True if the message has been read, false otherwise
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Sets whether the message has been read.
     * @param read True if the message has been read, false otherwise
     */
    public void setRead(boolean read) {
        isRead = read;
    }

    /**
     * Gets the timestamp when the message was created.
     * @return The timestamp when the message was created
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                ", createdAt=" + createdAt +
                '}';
    }
}
