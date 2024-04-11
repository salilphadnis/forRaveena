package dr.sparky.office.drsparkysoffice.controller;

import dr.sparky.office.drsparkysoffice.data.MedicalHistoryManager;
import dr.sparky.office.drsparkysoffice.data.UserManager;
import dr.sparky.office.drsparkysoffice.model.MedicalHistory;
import dr.sparky.office.drsparkysoffice.model.Patient;
import dr.sparky.office.drsparkysoffice.model.Vitals;
import dr.sparky.office.drsparkysoffice.view.SlideMenuView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the home page of the application.
 * This controller handles user interactions on the home view.
 */
public class PatientDashController implements Initializable {

    public ImageView menuImageViewId;
    public Pane rootPane;
    public Label titleLabel;
    public TableView<MedicalHistory> visitsTableView;
    public TextArea prescriptionTxtArea;
    public TextArea pharmacyTxtArea;
    private Patient patient;
    private List<MedicalHistory> medicalHistories;
    private ObservableList<MedicalHistory> observablePatients;
    private MedicalHistoryManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the home view and set up event handlers
        patient = UserManager.getCurrentUser().getPatient();
        medicalHistories = new MedicalHistoryManager().getMedicalHistories(patient.getPatientID());
        manager = new MedicalHistoryManager();

        titleLabel.setText("Welcome, " + patient.getFirstName() + " " + patient.getLastName());
        if (!medicalHistories.isEmpty()) {
            pharmacyTxtArea.setText(medicalHistories.get(0).getPharmacy());
            prescriptionTxtArea.setText(medicalHistories.get(0).getPrescription());
        }
        else {
            prescriptionTxtArea.setText("Prescription not available, if you have emergency call hotline 786");
            pharmacyTxtArea.setText("Pharmacy Details not available, if you have call hotline 786");
        }

        // Create the slide menu and buttons
        menuImageViewId.setOnMouseClicked(e -> {
            new SlideMenuView(rootPane).open();
        });

        initializeHistoryTable();
    }

    private void initializeHistoryTable() {
        TableColumn<MedicalHistory, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> {
            LocalDateTime date = cellData.getValue().getDate();
            String formattedDate = date != null ? date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "N/A";
            return new SimpleStringProperty(formattedDate);
        });

        // Weight Column
        TableColumn<MedicalHistory, String> weightColumn = new TableColumn<>("Weight");
        weightColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getVitals() != null ?
                        cellData.getValue().getVitals().getWeight() + " kg" : "N/A").asString());

        // Height Column
        TableColumn<MedicalHistory, String> heightColumn = new TableColumn<>("Height");
        heightColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getVitals() != null ?
                        cellData.getValue().getVitals().getHeight() + " cm" : "N/A").asString());

        // Blood Pressure Column
        TableColumn<MedicalHistory, String> temPressureColumn = new TableColumn<>("Blood Pressure");
        temPressureColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getVitals() != null ?
                        cellData.getValue().getVitals().getTemperature() : "N/A").asString());

        // Blood Pressure Column
        TableColumn<MedicalHistory, String> bloodPressureColumn = new TableColumn<>("Blood Pressure");
        bloodPressureColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getVitals() != null ?
                        cellData.getValue().getVitals().getBloodPressure() : "N/A").asString());

        // Blood Pressure Column
        TableColumn<MedicalHistory, Boolean> isDonePressureColumn = new TableColumn<>("Completed");
        isDonePressureColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().isVisitCompleted()));

        TableColumn<MedicalHistory, Void> viewColumn = new TableColumn<>("Actions");
        viewColumn.setCellFactory(getButtonCellFactory());

        visitsTableView.getColumns().clear();
        visitsTableView.getColumns().addAll(dateColumn, weightColumn, heightColumn, bloodPressureColumn, isDonePressureColumn, viewColumn);

        List<MedicalHistory> medicalHistories1 = manager.getMedicalHistories(patient.getPatientID());
        System.out.println(medicalHistories1);
        observablePatients = FXCollections.observableArrayList(manager.getMedicalHistories(patient.getPatientID()));
        observablePatients.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        visitsTableView.setItems(observablePatients);
    }

    private Callback<TableColumn<MedicalHistory, Void>, TableCell<MedicalHistory, Void>> getButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<MedicalHistory, Void> call(final TableColumn<MedicalHistory, Void> param) {
                return new TableCell<>() {
                    private final HBox hbox = new HBox(10); // Spacing between buttons
                    private Button viewBtn = new Button("View");

                    {
                        hbox.getChildren().addAll(viewBtn);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            MedicalHistory medicalHistory = getTableView().getItems().get(getIndex());

                            // Now inside updateItem, the table cell is properly associated with a TableView
                            viewBtn.setOnAction(event -> {
                                viewMedicalHistoryDetails(medicalHistory);
                            });

                            setGraphic(hbox);
                        }
                    }
                };
            }
        };
    }

    private void viewMedicalHistoryDetails(MedicalHistory medicalHistory) {
        // Create the custom dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Medical History Details");

        // Set the button types
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

        // Creating a grid pane to lay out the information
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Adding information to the grid
        grid.add(new Label("Date:"), 0, 0);
        grid.add(new Label(medicalHistory.getDate().toString()), 1, 0);

        // Displaying Vitals information
        Vitals vitals = medicalHistory.getVitals();
        if (vitals != null) {
            grid.add(new Label("Weight:"), 0, 1);
            grid.add(new Label(String.valueOf(vitals.getWeight()) + " kg"), 1, 1);
            grid.add(new Label("Height:"), 0, 2);
            grid.add(new Label(String.valueOf(vitals.getHeight()) + " cm"), 1, 2);
            grid.add(new Label("Temperature:"), 0, 3);
            grid.add(new Label(String.valueOf(vitals.getTemperature()) + " °C"), 1, 3);
            grid.add(new Label("Blood Pressure:"), 0, 4);
            grid.add(new Label(vitals.getBloodPressure()), 1, 4);
        }

        // Other MedicalHistory details
        grid.add(new Label("Allergies:"), 0, 5);
        grid.add(new TextField(medicalHistory.getAllergies()), 1, 5);
        grid.add(new Label("Pharmacy:"), 0, 7);
        grid.add(new TextField(medicalHistory.getPharmacy()), 1, 7);
        grid.add(new Label("Prescription:"), 0, 8);
        grid.add(new TextField(medicalHistory.getPrescription()), 1, 8);
        grid.add(new Label("Visit Completed:"), 0, 9);
        grid.add(new Label(medicalHistory.isVisitCompleted() ? "Yes" : "No"), 1, 9);

        // Set the dialog content
        dialog.getDialogPane().setContent(grid);

        // Show the dialog
        dialog.showAndWait();
    }

}
