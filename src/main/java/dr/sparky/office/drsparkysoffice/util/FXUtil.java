package dr.sparky.office.drsparkysoffice.util;

import dr.sparky.office.drsparkysoffice.SparkyApp;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class for loading FXML views and transferring data between controllers.
 */
public class FXUtil {

    public static final String START_VIEW = "start-view.fxml";
    public static final String LOGIN_PAGE = "login-view.fxml";
    public static final String REGISTER_PAGE = "register-view.fxml";
    public static final String PATIENT_ADD_PAGE = "add-patient-view.fxml";
    public static final String PATIENT_MESSAGE_PAGE = "patient-message-view.fxml";
    public static final String ALL_PATIENT_DETAILS_PAGE = "all-patient-details-view.fxml";
    public static final String PATIENT_PANEL_PAGE = "patient-panel-view.fxml";
    public static final String PATIENT_DASH_PAGE = "patient-dash-view.fxml";


    /**
     * Load an FXML view in a new stage and transfer data to its controller.
     *
     * @param event   The event that triggers the view loading.
     * @param fxSource The FXML file to load.
     * @param title    The title of the stage.
     * @param data     The data to transfer to the controller.
     */
    public static void loadView(Event event, String fxSource, String title, Object... data) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(SparkyApp.class.getResource(fxSource));
            Parent layout = loader.load();

            // Transfer data to the controller
            if (data.length > 0) {
                DataTraveler controller = loader.getController();
                controller.data(data);
            }

            // Show the new stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(layout);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
