package dr.sparky.office.drsparkysoffice.test;

import dr.sparky.office.drsparkysoffice.data.UserManager;
import dr.sparky.office.drsparkysoffice.model.UserAccount;
import dr.sparky.office.drsparkysoffice.model.UserType;

public class UserTest {

    public static void main(String[] args) {
        UserManager userManager = new UserManager();

        // Test adding a user
        UserAccount user = new UserAccount("testUser", "testPassword", UserType.DOCTOR, null);
        boolean added = userManager.addUser(user);
        System.out.println("User added: " + added);

        // Test retrieving a user
        UserAccount retrievedUser = userManager.getUser("testUser");
        System.out.println("Retrieved user: " + retrievedUser);

        // Test validating login
        userManager.addUser(user); // Add the user again for login validation test
        UserAccount validLogin = userManager.validateLogin("testUser", "testPassword");
        System.out.println("Valid login: " + validLogin);

        // Test updating a user
        boolean updated = userManager.updatePassword(user, "newPassword");
        System.out.println("Password updated: " + updated);

        // Test validating login
        validLogin = userManager.validateLogin("testUser", "testPassword");
        System.out.println("Valid login: " + validLogin);

        // Test deleting a user
        boolean deleted = userManager.deleteUser("testUser");
        System.out.println("User deleted: " + deleted);

        // Test validating login
        System.out.println(user);

        UserAccount user2 = new UserAccount("testUser", "testPassword", UserType.DOCTOR, null);
        userManager.addUser(user2);
        validLogin = userManager.validateLogin("testUser", "testPassword");
        System.out.println("Valid login: " + validLogin);

        UserAccount invalidLogin = userManager.validateLogin("testUser", "wrongPassword");
        System.out.println("Invalid login: " + invalidLogin);

        // Test deleting a user
//        deleted = userManager.deleteUser("testUser");
//        System.out.println("User deleted: " + deleted);
    }
}
