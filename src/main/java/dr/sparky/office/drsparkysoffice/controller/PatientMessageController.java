package dr.sparky.office.drsparkysoffice.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import dr.sparky.office.drsparkysoffice.data.MessagesManager;
import dr.sparky.office.drsparkysoffice.data.UserManager;
import dr.sparky.office.drsparkysoffice.model.MedicalHistory;
import dr.sparky.office.drsparkysoffice.model.Message;
import dr.sparky.office.drsparkysoffice.model.UserAccount;
import dr.sparky.office.drsparkysoffice.model.UserType;
import dr.sparky.office.drsparkysoffice.util.DataTraveler;
import dr.sparky.office.drsparkysoffice.util.FXUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 * Controller for the patient message page of the application.
 * This controller handles user interactions on the patient message view.
 */
public class PatientMessageController implements Initializable {

    public ImageView backImageViewId;
    public TableView<Message> inboxTableViewId;
    private ObservableList<Message> data;
    private MessagesManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new MessagesManager();
        initTableView();

        // Initialize back button
        backImageViewId.setOnMouseClicked(e -> {

            UserAccount currentUser = UserManager.getCurrentUser();
            if (currentUser.getType().equals(UserType.PATIENT)) {
                ((Node) e.getSource()).getScene().getWindow().hide();
                FXUtil.loadView(
                        e,
                        FXUtil.PATIENT_DASH_PAGE,
                        "Doctor App"
                );
            } else {
                ((Node) e.getSource()).getScene().getWindow().hide();
                FXUtil.loadView(
                        e,
                        FXUtil.ALL_PATIENT_DETAILS_PAGE,
                        UserManager.getCurrentUser().getType() + " App"
                );
            }
        });

    }

    private void initTableView() {
        // Initialize TableView with sample data
        data = FXCollections.observableArrayList(manager.getMessages(UserManager.getCurrentUser()));

        // Define columns
        TableColumn<Message, String> sender = new TableColumn<>("Sender");
        sender.setCellValueFactory(new PropertyValueFactory<>("sender"));

        TableColumn<Message, String> reciver = new TableColumn<>("Receiver");
        reciver.setCellValueFactory(new PropertyValueFactory<>("receiver"));

        TableColumn<Message, String> message = new TableColumn<>("Message");
        message.setCellValueFactory(new PropertyValueFactory<>("message"));

        TableColumn<Message, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> {
            LocalDateTime date = cellData.getValue().getCreatedAt();
            String formattedDate = date != null ? date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "N/A";
            return new SimpleStringProperty(formattedDate);
        });

        // Add columns to table view
        inboxTableViewId.getColumns().add(sender);
        inboxTableViewId.getColumns().add(reciver);
        inboxTableViewId.getColumns().add(message);
        inboxTableViewId.getColumns().add(dateColumn);

        // Set items in the TableView
        inboxTableViewId.setItems(data);

        // Apply CSS styling to the TableView to match the image style
        inboxTableViewId.setStyle("-fx-border-color: grey; -fx-table-cell-border-color: grey;");
        inboxTableViewId.setPrefHeight(10 * 24); // Assuming each row is 24 pixels in height
        inboxTableViewId.refresh();

    }

    public void addMessageButtonAction(ActionEvent actionEvent) {

    }

    private void initializeHistoryTable() {


        // Weight Column
        TableColumn<Message, String> weightColumn = new TableColumn<>("Sneder");
        weightColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getSender() != null ?
                        cellData.getValue().getSender()+ " kg" : "N/A").asString());

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

        TableColumn<Message, String> dateColumn = new TableColumn<>("CreatedAt");
        dateColumn.setCellValueFactory(cellData -> {
            LocalDateTime date = cellData.getValue().getCreatedAt();
            String formattedDate = date != null ? date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "N/A";
            return new SimpleStringProperty(formattedDate);
        });


        inboxTableViewId.getColumns().clear();
      //  inboxTableViewId.getColumns().addAll(dateColumn, weightColumn, heightColumn);
//
//        data = FXCollections.observableArrayList(manager.gw(.getPatientID()));
//        data.sort(new Comparator<MedicalHistory>() {
//            @Override
//            public int compare(MedicalHistory o1, MedicalHistory o2) {
//                return o2.getDate().compareTo(o1.getDate());
//            }
//        });
//        hi.setItems(observablePatients);
    }
}