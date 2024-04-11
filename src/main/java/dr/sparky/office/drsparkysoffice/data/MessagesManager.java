package dr.sparky.office.drsparkysoffice.data;

import dr.sparky.office.drsparkysoffice.model.Message;
import dr.sparky.office.drsparkysoffice.model.UserAccount;

import java.io.*;
import java.util.*;

/**
 * Manages the sending, receiving, and storage of messages.
 */
public class MessagesManager {

    // Path to the messages file
    private static final String MESSAGES_FILE = "database/messages.bin";

    // Map to store messages with usernames as keys
    private Map<String, List<Message>> messagesMap;

    /**
     * Constructs a MessagesManager object and loads messages from the messages file.
     */
    public MessagesManager() {
        loadMessages();
    }

    /**
     * Loads messages from the messages file into the messages map.
     * If the file does not exist, initializes an empty messages map.
     */
    private void loadMessages() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MESSAGES_FILE))) {
            messagesMap = (Map<String, List<Message>>) ois.readObject();
        } catch (FileNotFoundException e) {
            messagesMap = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load messages", e);
        }
    }

    /**
     * Saves the messages map to the messages file.
     */
    public void saveMessages() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MESSAGES_FILE))) {
            oos.writeObject(messagesMap);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save messages", e);
        }
    }

    /**
     * Sends a message to the specified receiver.
     *
     * @param receiverUsername The username of the message receiver
     * @param message          The message to be sent
     */
    public void sendMessage(String receiverUsername, Message message) {
        List<Message> userMessages = messagesMap.getOrDefault(receiverUsername, new ArrayList<>());
        userMessages.add(message);
        messagesMap.put(receiverUsername, userMessages);
        saveMessages();
    }

    /**
     * Retrieves the messages for the specified username.
     *
     * @param username The username of the user whose messages are to be retrieved
     * @return The list of messages for the specified username
     */
    public List<Message> getMessages(String username) {
        return messagesMap.getOrDefault(username, new ArrayList<>());
    }

    public List<Message> getMessages(UserAccount account) {
        List<Message> list = new ArrayList<>();
        for (List<Message> messages : messagesMap.values()) {
            for (Message message : messages) {
                if (message.getReceiver().getEmail().equals(account.getEmail())
                        || message.getSender().getEmail().equals(account.getEmail())) {
                    list.add(message);
                }
            }
        }

        list.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        return list;
    }

    /**
     * Marks a message as read for the specified username and message index.
     *
     * @param username     The username of the user
     * @param messageIndex The index of the message to be marked as read
     * @return True if the message was successfully marked as read, false otherwise
     */
    public boolean markAsRead(String username, int messageIndex) {
        List<Message> userMessages = messagesMap.getOrDefault(username, new ArrayList<>());
        if (messageIndex >= 0 && messageIndex < userMessages.size()) {
            userMessages.get(messageIndex).setRead(true);
            saveMessages();
            return true;
        }
        return false;
    }

    /**
     * Deletes a message for the specified username and message index.
     *
     * @param username     The username of the user
     * @param messageIndex The index of the message to be deleted
     * @return True if the message was successfully deleted, false otherwise
     */
    public boolean deleteMessage(String username, int messageIndex) {
        List<Message> userMessages = messagesMap.getOrDefault(username, new ArrayList<>());
        if (messageIndex >= 0 && messageIndex < userMessages.size()) {
            userMessages.remove(messageIndex);
            saveMessages();
            return true;
        }
        return false;
    }
}
