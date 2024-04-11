package dr.sparky.office.drsparkysoffice.test;

import dr.sparky.office.drsparkysoffice.data.MessagesManager;
import dr.sparky.office.drsparkysoffice.model.Message;
import dr.sparky.office.drsparkysoffice.model.UserAccount;
import dr.sparky.office.drsparkysoffice.model.UserType;

public class MessagesManagerTest {

    public static void main(String[] args) {
        MessagesManager messagesManager = new MessagesManager();

        // Test sending a message
        UserAccount sender = new UserAccount("sender", "senderPassword", UserType.DOCTOR, null);
        UserAccount receiver = new UserAccount("receiver", "receiverPassword", UserType.PATIENT, null);
        Message message = new Message(sender, receiver, "Test message");
        messagesManager.sendMessage(receiver.getEmail(), message);
        System.out.println("Message sent");

        // Test retrieving messages
        System.out.println("Retrieved messages for receiver: " + messagesManager.getMessages(receiver.getEmail()));

        // Test marking a message as read
        int messageIndex = 0; // Index of the message to mark as read
        boolean markedAsRead = messagesManager.markAsRead(receiver.getEmail(), messageIndex);
        System.out.println("Message marked as read: " + markedAsRead);

        // Test deleting a message
        boolean deleted = messagesManager.deleteMessage(receiver.getEmail(), messageIndex);
        System.out.println("Message deleted: " + deleted);
    }
}

