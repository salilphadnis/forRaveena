package dr.sparky.office.drsparkysoffice.controller;

import dr.sparky.office.drsparkysoffice.data.PatientManager;
import dr.sparky.office.drsparkysoffice.data.UserManager;
import dr.sparky.office.drsparkysoffice.model.*;
import dr.sparky.office.drsparkysoffice.util.DataTraveler;
import dr.sparky.office.drsparkysoffice.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Controller for the registration page of the application.
 * This controller handles user interactions on the registration view.
 */
public class RegisterController implements Initializable, DataTraveler {

    public TextField emailTxtFldId;
    public TextField passwordTxtFldId;
    public TextField firstNameTxtFldId;
    public TextField lastNameTxtFldId;
    public TextField dobTxtFldId;
    public TextField phoneTxtFldId;
    public TextField insProviderTxtFldID;
    public TextField emConFNameTxtFldID;
    public TextField emConLNameTxtFldID;
    public TextField emConPhoneTxtFldID;
    public Label errMsgLabelId;

    private PatientManager patientManager;
    private UserManager userManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientManager = new PatientManager();
        userManager = new UserManager();
    }

    @Override
    public void data(Object... o) {
        // Implement data transfer functionality if needed
    }

    // Submit registration and move to the home page
    public void submitButtonAction(ActionEvent actionEvent) {
        errMsgLabelId.setText(""); // Clear any previous error messages

        // Validate input fields and email uniqueness
        if (!validateFields() || !isEmailUnique(emailTxtFldId.getText())) {
            return; // If validation fails, the error message is already set
        }

        try {
            Patient patient = createPatient();
            UserAccount userAccount = createUserAccount(patient);

            // Assuming UserManager and PatientManager have been correctly implemented to handle file operations
            boolean patientSaved = patientManager.addPatient(patient);
            boolean userSaved = userManager.addUser(userAccount);

            if (patientSaved && userSaved) {
                // Proceed to load the home page
                UserManager.setCurrentUser(userAccount);
                FXUtil.loadView(actionEvent, FXUtil.PATIENT_DASH_PAGE, "Patient Dashboard");
            } else {
                throw new Exception("There was an error saving the patient or user account.");
            }
        } catch (Exception e) {
            errMsgLabelId.setText(e.getMessage());
        }
    }

    private boolean validateFields() {
        // Check if any text field is empty
        if (emailTxtFldId.getText().isEmpty() ||
                passwordTxtFldId.getText().isEmpty() ||
                firstNameTxtFldId.getText().isEmpty() ||
                lastNameTxtFldId.getText().isEmpty() ||
                dobTxtFldId.getText().isEmpty() ||
                phoneTxtFldId.getText().isEmpty() ||
                insProviderTxtFldID.getText().isEmpty() ||
                emConFNameTxtFldID.getText().isEmpty() ||
                emConLNameTxtFldID.getText().isEmpty() ||
                emConPhoneTxtFldID.getText().isEmpty()) {

            errMsgLabelId.setText("All fields are required.");
            return false;
        }

        // Validate email format
        if (!isValidEmail(emailTxtFldId.getText())) {
            errMsgLabelId.setText("Invalid email format.");
            return false;
        }

        // Validate email format
        if (!isEmailUnique(emailTxtFldId.getText())) {
            errMsgLabelId.setText("Entered email already registered.");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }


    private boolean isEmailUnique(String email) {
        // Implement logic to check if the email is unique
        // This could involve checking the email against the list of users in UserManager
        return userManager.getUser(email) == null;
    }

    private Patient createPatient() {
        // Replace the String conversions as necessary based on the expected format
        // For example, you might need to parse the dateOfBirth into a Date object
        String firstName = firstNameTxtFldId.getText();
        String lastName = lastNameTxtFldId.getText();
        String email = emailTxtFldId.getText();
        String phoneNumber = phoneTxtFldId.getText();
        String dateOfBirth = dobTxtFldId.getText(); // or parse into Date
        String insuranceProvider = insProviderTxtFldID.getText();
        String emergencyContactFirstName = emConFNameTxtFldID.getText();
        String emergencyContactLastName = emConLNameTxtFldID.getText();
        String emergencyContactPhoneNumber = emConPhoneTxtFldID.getText();

        // Assuming your Patient class has a constructor that accepts all these fields
        // If not, create a new Patient object and set these fields using setters
        return new Patient(
                // Use appropriate values, including conversion or parsing as needed
                generatePatientID(), email,firstName, lastName, dateOfBirth, phoneNumber, insuranceProvider,
                emergencyContactFirstName, emergencyContactLastName, emergencyContactPhoneNumber
        );
    }

    // Find unique id for patient
    private String generatePatientID() {
        Random rand = new Random();
        String format = String.format("%05d", rand.nextInt(100000));
        while (patientManager.retrievePatient(format) != null) {
            format = String.format("%05d", rand.nextInt(100000));
        }
        return format;
    }


    private UserAccount createUserAccount(Patient patient) {
        // Assuming UserAccount has a constructor or setters for all the fields
        return new UserAccount(
                emailTxtFldId.getText(),
                passwordTxtFldId.getText(), // You should hash the password
                UserType.PATIENT,
                patient
        );
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.START_VIEW,
                 "Dr Sparky's Office"
        );
    }
}
