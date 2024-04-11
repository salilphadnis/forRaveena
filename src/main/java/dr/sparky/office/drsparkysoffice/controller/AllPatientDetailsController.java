package dr.sparky.office.drsparkysoffice.controller;

import dr.sparky.office.drsparkysoffice.data.PatientManager;
import dr.sparky.office.drsparkysoffice.data.UserManager;
import dr.sparky.office.drsparkysoffice.model.Patient;
import dr.sparky.office.drsparkysoffice.model.UserAccount;
import dr.sparky.office.drsparkysoffice.model.UserType;
import dr.sparky.office.drsparkysoffice.util.DataTraveler;
import dr.sparky.office.drsparkysoffice.util.FXUtil;
import dr.sparky.office.drsparkysoffice.view.SlideMenuView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the patient details page of the application.
 * This controller handles user interactions on the patient details view.
 */
public class AllPatientDetailsController implements Initializable {

    public ImageView backImageViewId;
    public Button searchBtnId;
    public TextField searchTxtFldId;
    public TableView<Patient> tableViewId;
    public ImageView addImageViewId;
    public Pane rootPane;
    private PatientManager patientManager;
    private ObservableList<Patient> observablePatients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientManager = new PatientManager();

        // Initialize Patient data tables
        initTableView();

        // Initialize back button
        backImageViewId.setOnMouseClicked(e -> {
            new SlideMenuView(rootPane).open();
        });

        // Initialize search button
        searchBtnId.setOnAction(e -> searchPatient());

        // Initialize add patient button
        addImageViewId.setOnMouseClicked(e -> {
             FXUtil.loadView(e, FXUtil.PATIENT_ADD_PAGE, "Add New Patient");
        });

    }

    private void initTableView() {
        observablePatients = FXCollections.observableArrayList(patientManager.getAllPatients());
        tableViewId.setItems(observablePatients);

        // Initialize back button
        backImageViewId.setOnMouseClicked(e -> {
            new SlideMenuView(rootPane).open();
        });

        // Initialize search button
        searchBtnId.setOnAction(e -> searchPatient());

        // Initialize add patient button
        addImageViewId.setOnMouseClicked(e -> {
            FXUtil.loadView(e, FXUtil.PATIENT_ADD_PAGE, "Add New Patient");
        });

        // Set up table columns
        TableColumn<Patient, String> patientIDColumn = new TableColumn<>("Patient ID");
        patientIDColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));

        TableColumn<Patient, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Patient, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Patient, String> dateOfBirthColumn = new TableColumn<>("Date Of Birth");
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        TableColumn<Patient, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Add columns to table
        tableViewId.getColumns().addAll(patientIDColumn, firstNameColumn, lastNameColumn, dateOfBirthColumn,phoneNumberColumn);

        // Create a custom cell factory for the view button
        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                final TableCell<Patient, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Manage");
                    {
                        btn.setOnAction(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            FXUtil.loadView(event, FXUtil.PATIENT_PANEL_PAGE, "Patient Details View", patient);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        // Add a new column with the custom cell factory
        TableColumn<Patient, Void> viewButtonColumn = new TableColumn<>("Manage");
        viewButtonColumn.setCellFactory(cellFactory);

        tableViewId.getColumns().add(viewButtonColumn);
    }


    // Search for patients based on the search text
    private void searchPatient() {
        String searchText = searchTxtFldId.getText().toLowerCase().trim();
        List<Patient> filteredPatients = patientManager.getAllPatients().stream()
                .filter(patient -> patient.getFirstName().toLowerCase().contains(searchText)
                        || patient.getLastName().toLowerCase().contains(searchText)
                        || patient.getEmail().toLowerCase().contains(searchText)
                        || patient.getPatientID().toLowerCase().contains(searchText)
                        || patient.getPhoneNumber().contains(searchText)
                        || patient.getDateOfBirth().contains(searchText))
                .collect(Collectors.toList());
        observablePatients.clear();
        observablePatients.addAll(filteredPatients);
    }
}
