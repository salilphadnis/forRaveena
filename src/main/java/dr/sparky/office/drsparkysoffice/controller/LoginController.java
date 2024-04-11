package dr.sparky.office.drsparkysoffice.controller;

import dr.sparky.office.drsparkysoffice.data.UserManager;
import dr.sparky.office.drsparkysoffice.model.UserAccount;
import dr.sparky.office.drsparkysoffice.model.UserType;
import dr.sparky.office.drsparkysoffice.util.DataTraveler;
import dr.sparky.office.drsparkysoffice.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the login page of the application.
 * This controller handles user interactions on the login view.
 */
public class LoginController implements Initializable, DataTraveler {

    public TextField userNameTxtFldID;
    public PasswordField passwordTxtFldID;
    public Label errLblId;

    private UserManager userManager;
    private UserType type;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userManager = new UserManager();
    }

    @Override
    public void data(Object... o) {
        type = (UserType) o[0];
    }

    // Method to handle sign in action
    public void signInButtonAction(ActionEvent actionEvent) {

        String username = userNameTxtFldID.getText();
        String password = passwordTxtFldID.getText();
        UserAccount userAccount = userManager.validateLogin(username, password); // validate login

        if (userAccount == null) {
            errLblId.setText("Invalid username or password!");
        } else if (!userAccount.getType().equals(type)) {
            errLblId.setText("Invalid login type selection!");
        } else {
            if (type.equals(UserType.PATIENT)) {
                // Hide the current window
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                // Load the home page
                FXUtil.loadView(
                        actionEvent,
                        FXUtil.PATIENT_DASH_PAGE,
                        "Patient Dashboard"
                );
            } else if (type.equals(UserType.DOCTOR)) {
                // Hide the current window
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                // Load the home page
                FXUtil.loadView(
                        actionEvent,
                        FXUtil.ALL_PATIENT_DETAILS_PAGE,
                        "Doctor Dashboard"
                );
            } else if (type.equals(UserType.NURSE)) {
                // Hide the current window
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                // Load the home page
                FXUtil.loadView(
                        actionEvent,
                        FXUtil.ALL_PATIENT_DETAILS_PAGE,
                        "Nurse Dashboard"
                );
            }
        }
    }

    public void backButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(
                actionEvent,
                FXUtil.START_VIEW,
                "Doctor App"
        );
    }
}


