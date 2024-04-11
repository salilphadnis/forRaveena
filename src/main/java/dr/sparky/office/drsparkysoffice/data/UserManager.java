package dr.sparky.office.drsparkysoffice.data;

import dr.sparky.office.drsparkysoffice.model.UserAccount;
import dr.sparky.office.drsparkysoffice.model.UserType;
import dr.sparky.office.drsparkysoffice.model.Patient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages user account data, including CRUD operations and password encryption.
 */
public class UserManager {

    private static final String USER_ACCOUNTS_FILE = "user_accounts.txt";
    private final Map<String, UserAccount> userAccounts;
    private static UserAccount currentUser;

    public UserManager() {
        this.userAccounts = new HashMap<>();
        // Load the user accounts from the file when the manager is instantiated
        loadUserAccounts();
    }

    // Saves the user accounts to a file
    public void saveUserAccounts() throws IOException {
        Path path = Paths.get(USER_ACCOUNTS_FILE);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (UserAccount account : userAccounts.values()) {
                String patientId = account.getPatient() != null ? account.getPatient().getPatientID() : ""; // Use -1 to signify no patient
                String line = String.format("%s|%s|%s|%s%n",
                        account.getEmail(),
                        account.getPassword(), // Assuming the password is already encrypted
                        account.getType(),
                        patientId);
                writer.write(line);
            }
        }
    }

    // Loads the user accounts from a file
    public void loadUserAccounts() {
        Path path = Paths.get(USER_ACCOUNTS_FILE);
        if (!Files.exists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String username = parts[0];
                String password = parts[1];
                UserType type = UserType.valueOf(parts[2]);

                try {
                    String patientId = parts[3];
                    Patient patient = !patientId.equals("") ? retrievePatientById(patientId) : null; // You need to implement this method
                    UserAccount account = new UserAccount(username, password, type, patient);
                    userAccounts.put(username, account);
                } catch (Exception e) {
                    UserAccount account = new UserAccount(username, password, type);
                    userAccounts.put(username, account);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a Patient by their ID
    private Patient retrievePatientById(String patientId) {
        PatientManager manager = new PatientManager();
        return manager.retrievePatient(patientId);
    }

    /**
     * Adds a new user account.
     *
     * @param user The UserAccount to add
     * @return True if the account was added successfully, false otherwise
     */
    public boolean addUser(UserAccount user) {
        if (userAccounts.containsKey(user.getEmail())) {
            return false; // User already exists
        }

        // Encrypt password before storing
        user.setPassword(encryptPassword(user.getPassword()));
        userAccounts.put(user.getEmail(), user);

        try {
            saveUserAccounts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Retrieves a user account by username.
     *
     * @param username The username of the account to retrieve
     * @return The UserAccount if found, otherwise null
     */
    public UserAccount getUser(String username) {
        return userAccounts.get(username);
    }

    /**
     * Updates a user account.
     *
     * @param user The UserAccount with updated information
     * @return True if the account was updated successfully, false otherwise
     */
    public boolean updateUser(UserAccount user) {
        if (!userAccounts.containsKey(user.getEmail())) {
            return false; // User does not exist
        }
        userAccounts.put(user.getEmail(), user);

        try {
            saveUserAccounts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Deletes a user account.
     *
     * @param username The username of the account to delete
     * @return True if the account was deleted successfully, false otherwise
     */
    public boolean deleteUser(String username) {
        if (userAccounts.containsKey(username)) {
            userAccounts.remove(username);
            try {
                saveUserAccounts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        return false;
    }

    /**
     * Validates a user's login credentials.
     *
     * @param username The username to validate
     * @param password The password to validate
     * @return True if the credentials are valid, false otherwise
     */
    public UserAccount validateLogin(String username, String password) {
        UserAccount user = getUser(username);
        String encryptedPassword = encryptPassword(password);
        if (user != null && encryptedPassword.equals(user.getPassword())) {
            currentUser = user;
            return user;
        }
        return null;
    }

    // Encrypts the password using SHA-256
    private String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not find hashing algorithm", e);
        }
    }


    // Converts a byte array into a hex string
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean updatePassword(UserAccount user, String newPassword) {
        if (!userAccounts.containsKey(user.getEmail())) {
            return false; // User not exists
        }

        // Encrypt password before storing
        UserAccount u = userAccounts.get(user.getEmail());
        u.setPassword(encryptPassword(newPassword));
        userAccounts.put(user.getEmail(), u);

        try {
            saveUserAccounts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Retrieves the map of user accounts.
     * @return The map of user accounts
     */
    public Map<String, UserAccount> getUserAccounts() {
        return userAccounts;
    }

    /**
     * Retrieves the currently logged-in user.
     * @return The currently logged-in user
     */
    public static UserAccount getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the currently logged-in user.
     * @param currentUser The user account to set as the current user
     */
    public static void setCurrentUser(UserAccount currentUser) {
        UserManager.currentUser = currentUser;
    }

    /**
     * Logs out the current user by setting the currentUser to null.
     */
    public static void logout() {
        UserManager.currentUser = null;
    }
}
