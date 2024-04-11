package dr.sparky.office.drsparkysoffice.controller;

import dr.sparky.office.drsparkysoffice.data.MedicalHistoryManager;
import dr.sparky.office.drsparkysoffice.data.UserManager;
import dr.sparky.office.drsparkysoffice.model.MedicalHistory;
import dr.sparky.office.drsparkysoffice.model.Patient;
import dr.sparky.office.drsparkysoffice.model.Vitals;
import dr.sparky.office.drsparkysoffice.util.DataTraveler;
import dr.sparky.office.drsparkysoffice.util.FXUtil;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the patient details page of the application.
 * This controller handles user interactions on the patient details view.
 */
public class PatientPanelController implements Initializable, DataTraveler {

    public TableView<MedicalHistory> historyTableId;
    public Pane rootPane;
    public ImageView backImageViewId;

    public Label nameLblId;
    public Label emailLblId;
    public Label ageLblId;
    public Label phoneLblId;
    public Button addVitalBtn;
    public TextArea currentPharmacyTxtId;

    private Patient patient;
    private MedicalHistoryManager historyManager;
    private ObservableList<MedicalHistory> observablePatients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        historyManager = new MedicalHistoryManager();

        backImageViewId.setOnMouseClicked(e -> {
            if (UserManager.getCurrentUser().isDoctor())
                FXUtil.loadView(e, FXUtil.ALL_PATIENT_DETAILS_PAGE, "Doctor View");
            else
                FXUtil.loadView(e, FXUtil.ALL_PATIENT_DETAILS_PAGE, "Nurse View");
        });

    }

    private void initPatientInfo() {
        nameLblId.setText("Full-name: " + patient.getFirstName() + " " + patient.getLastName());
        emailLblId.setText("Email: " + patient.getEmail());
        ageLblId.setText("Date of Birth: " + patient.getDateOfBirth());
        nameLblId.setText("Phone: " + patient.getPhoneNumber());
    }

    @Override
    public void data(Object... o) {
        patient = (Patient) o[0];
        initPatientInfo();

        // if doctor
        if (UserManager.getCurrentUser().isDoctor()) {
            addVitalBtn.setVisible(false);
        }

        initializeHistoryTable();
        updatePharmacy();
    }

    public void updatePharmacy() {
        // init current pharmacy
        List<MedicalHistory> medicalHistories = historyManager.getMedicalHistories(patient.getPatientID());
        if (!medicalHistories.isEmpty()) {
            MedicalHistory medicalHistory = medicalHistories.get(medicalHistories.size() - 1);

            String pharmacy = medicalHistory.getPharmacy();
            if (pharmacy == null || pharmacy.isEmpty())
                currentPharmacyTxtId.setText("For last visit Pharmacy not assignment yet.");
            else
                currentPharmacyTxtId.setText(pharmacy);
        }
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

        historyTableId.getColumns().clear();
        historyTableId.getColumns().addAll(dateColumn, weightColumn, heightColumn, bloodPressureColumn, isDonePressureColumn, viewColumn);

        observablePatients = FXCollections.observableArrayList(historyManager.getMedicalHistories(patient.getPatientID()));
        observablePatients.sort(new Comparator<MedicalHistory>() {
            @Override
            public int compare(MedicalHistory o1, MedicalHistory o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        historyTableId.setItems(observablePatients);
    }

    private Callback<TableColumn<MedicalHistory, Void>, TableCell<MedicalHistory, Void>> getButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<MedicalHistory, Void> call(final TableColumn<MedicalHistory, Void> param) {
                return new TableCell<>() {
                    private final HBox hbox = new HBox(10); // Spacing between buttons
                    private Button viewBtn = new Button("View");
                    private Button prescribe = new Button("Prescribe");

                    {
                        hbox.getChildren().addAll(viewBtn, prescribe);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            MedicalHistory medicalHistory = getTableView().getItems().get(getIndex());

                            // add button based on logged user
                            if (!(UserManager.getCurrentUser().isDoctor() && !medicalHistory.isVisitCompleted())) {
                                prescribe.setVisible(false);
                            }

                            // Now inside updateItem, the table cell is properly associated with a TableView
                            viewBtn.setOnAction(event -> {
                                viewMedicalHistoryDetails(medicalHistory);
                            });

                            prescribe.setOnAction(event -> {
                                prescribe(medicalHistory);
                            });

                            setGraphic(hbox);
                        }
                    }
                };
            }
        };
    }

    private void prescribe(MedicalHistory medicalHistory) {
        // Create the custom dialog.
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Prescribe Medication");

        // Set the button types.
        ButtonType prescribeButtonType = new ButtonType("Prescribe", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(prescribeButtonType, ButtonType.CANCEL);

        // Create labels and text fields for input
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField allergiesField = new TextField(medicalHistory.getAllergies());
        TextField pharmacyField = new TextField(medicalHistory.getPharmacy());
        TextField prescriptionField = new TextField(medicalHistory.getPrescription());

        grid.add(new Label("Allergies:"), 0, 0);
        grid.add(allergiesField, 1, 0);
        grid.add(new Label("Pharmacy:"), 0, 1);
        grid.add(pharmacyField, 1, 1);
        grid.add(new Label("Prescription:"), 0, 2);
        grid.add(prescriptionField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Process the result.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == prescribeButtonType) {
                medicalHistory.setAllergies(allergiesField.getText());
                medicalHistory.setPharmacy(pharmacyField.getText());
                medicalHistory.setPrescription(prescriptionField.getText());
                medicalHistory.setVisitCompleted(true);

                historyManager.updateMedicalHistory(patient.getPatientID(), medicalHistory.getHistoryId(), medicalHistory);

                initializeHistoryTable();
                currentPharmacyTxtId.setText(medicalHistory.getPharmacy());
            }
            return null;
        });

        // Show the dialog and wait for the user response.
        dialog.showAndWait();
    }


    private void viewMedicalHistoryDetails(MedicalHistory medicalHistory) {
        // Create the custom dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Medical History Details");

        // Set the button types
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

        // Creating a grid pane to layout the information
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

    public void addVitalsButtonAction(ActionEvent actionEvent) {
        // Create the custom dialog.
        Dialog<Vitals> dialog = new Dialog<>();
        dialog.setTitle("Add Vitals");

        // Set the button types.
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the weight, height, temperature and blood pressure labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField weightField = new TextField();
        weightField.setPromptText("Weight");
        TextField heightField = new TextField();
        heightField.setPromptText("Height");
        TextField temperatureField = new TextField();
        temperatureField.setPromptText("Temperature");
        TextField bloodPressureField = new TextField();
        bloodPressureField.setPromptText("Blood Pressure");

        grid.add(new Label("Weight:"), 0, 0);
        grid.add(weightField, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(heightField, 1, 1);
        grid.add(new Label("Temperature:"), 0, 2);
        grid.add(temperatureField, 1, 2);
        grid.add(new Label("Blood Pressure:"), 0, 3);
        grid.add(bloodPressureField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the weight field by default.
        Platform.runLater(weightField::requestFocus);

        // Convert the result to a Vitals object when the add button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    int weight = Integer.parseInt(weightField.getText());
                    double height = Double.parseDouble(heightField.getText());
                    double temperature = Double.parseDouble(temperatureField.getText());
                    String bloodPressure = bloodPressureField.getText();

                    return new Vitals(weight, height, temperature, bloodPressure);
                } catch (NumberFormatException e) {
                    // Handle the format exception if input is not in valid format
                    System.out.println("Invalid input format");
                    return null;
                }
            }
            return null;
        });

        // Process the dialog result
        dialog.showAndWait().ifPresent(vitals -> {
            // Update the patient data
            MedicalHistory medicalHistory = new MedicalHistory(patient.getPatientID(), vitals);
            historyManager.addMedicalHistory(patient.getPatientID(), medicalHistory);

            observablePatients.add(medicalHistory);
            initializeHistoryTable();

            System.out.println(historyManager.getMedicalHistories(patient.getPatientID()));
            System.out.println("Vitals added: " + vitals);
        });
    }

    public void messagesVitalsButtonAction(ActionEvent actionEvent) {
        FXUtil.loadView(actionEvent, FXUtil.PATIENT_MESSAGE_PAGE, "Messages View");
    }
}
