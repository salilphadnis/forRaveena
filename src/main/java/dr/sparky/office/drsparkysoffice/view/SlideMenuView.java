package dr.sparky.office.drsparkysoffice.view;

import dr.sparky.office.drsparkysoffice.data.UserManager;
import dr.sparky.office.drsparkysoffice.model.UserType;
import dr.sparky.office.drsparkysoffice.util.FXUtil;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import static javafx.scene.text.Font.font;

/**
 * A custom JavaFX VBox representing a slide menu.
 * The menu can contain buttons for navigating to different parts of the application.
 */
public class SlideMenuView extends VBox {

    // Buttons for different menu options
    private Button patientsMessageBtn;
    private Button closeMenuBtn;
    private Button logoutBtn;

    /**
     * Constructs a SlideMenuView object.
     * Initializes and configures the menu buttons and layout.
     * @param pane The parent Pane to which this SlideMenuView is added.
     */
    public SlideMenuView(Pane pane) {
        configureButtons(); // Configure buttons for the menu
        configureLayout();  // Configure the layout of the menu
        pane.getChildren().addAll(this); // Add this menu to the specified parent Pane
    }

    // Method to configure buttons in the menu
    private void configureButtons() {
        // Create a title label for the menu
        Label title = new Label("Main Menu");
        title.setFont(font("System", FontWeight.BOLD, 24)); // Set font style
        title.setTextFill(Color.WHITE); // Set text color

        // Initialize menu buttons
        patientsMessageBtn = new Button("Messages");
        logoutBtn = new Button("Logout");
        closeMenuBtn = new Button("Close");

        // Apply styles to buttons
        String buttonStyle = "-fx-border-color: white; -fx-border-width: 1; -fx-text-fill: white; -fx-background-color: transparent;";
        patientsMessageBtn.setStyle(buttonStyle);
        closeMenuBtn.setStyle(buttonStyle);
        logoutBtn.setStyle(buttonStyle);

        // Set buttons to expand to the maximum available width
        patientsMessageBtn.setMaxWidth(Double.MAX_VALUE);
        closeMenuBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setMaxWidth(Double.MAX_VALUE);

        // Customize menu based on user type
        if (UserManager.getCurrentUser() != null && UserManager.getCurrentUser().getType().equals(UserType.PATIENT)) {
            getChildren().addAll(title, patientsMessageBtn, logoutBtn, closeMenuBtn);
        } else {
            getChildren().addAll(title, patientsMessageBtn, logoutBtn, closeMenuBtn);
        }

        // Event handler for the close menu button
        closeMenuBtn.setOnAction(event -> {
            close(); // Close the menu
        });

        // Event handler for navigating to the messages view
        patientsMessageBtn.setOnAction(e -> {
            ((Node) e.getSource()).getScene().getWindow().hide(); // Hide the current window
            FXUtil.loadView(
                    e,
                    FXUtil.PATIENT_MESSAGE_PAGE,
                    "Patients Messages"
            );
        });

        // Event handler for logging out
        logoutBtn.setOnAction(e -> {
            ((Node) e.getSource()).getScene().getWindow().hide(); // Hide the current window
            FXUtil.loadView(
                    e,
                    FXUtil.START_VIEW,
                    "Dr Sparky's Office"
            );
            UserManager.logout(); // Log out the user
        });
    }

    // Method to configure the layout of the menu
    private void configureLayout() {
        setTranslateX(-200); // Start off-screen

        // Set background color to red
        setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        // Set fixed height and width
        setPrefHeight(650);
        setPrefWidth(200);

        setPadding(new Insets(20)); // Set padding
        setSpacing(10); // Set spacing between elements
    }

    // Method to open the menu with sliding animation
    public void open() {
        animateSlide(0); // Slide in
    }

    // Method to close the menu with sliding animation
    public void close() {
        animateSlide(-200); // Slide out
    }

    // Method to animate the sliding transition of the menu
    private void animateSlide(double toX) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.25), this);
        transition.setFromX(getTranslateX());
        transition.setToX(toX);
        transition.play();
    }
}
