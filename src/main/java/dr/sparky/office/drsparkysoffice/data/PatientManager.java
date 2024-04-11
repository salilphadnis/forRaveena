package dr.sparky.office.drsparkysoffice.data;

import dr.sparky.office.drsparkysoffice.model.Patient;
import java.util.List;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Manages patient data, including retrieval, storage, update, and deletion.
 */
public class PatientManager {

    // Attributes
    private static List<Patient> allPatients;
    private static final String DIRECTORY = "patients";

    // Constructor
    public PatientManager() {
        allPatients = new ArrayList<>();
        loadAllPatients();
    }

    /**
     * Retrieves a patient by their ID.
     * @param patientID The ID of the patient to retrieve
     * @return The patient if found, otherwise null
     */
    public Patient retrievePatient(String patientID) {
        return allPatients.stream()
                .filter(patient -> patient.getPatientID().equals(patientID))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new patient.
     * @param patient The patient to add
     * @return True if the patient was added successfully, false otherwise
     */
    public boolean addPatient(Patient patient) {
        if (retrievePatient(patient.getPatientID()) != null) {
            return false; // Patient with the same ID already exists
        }
        allPatients.add(patient);
        return savePatient(patient);
    }

    /**
     * Updates a patient's information.
     * @param patient The patient with updated information
     * @return True if the update was successful, false otherwise
     */
    public boolean updatePatient(Patient patient) {
        int index = allPatients.indexOf(retrievePatient(patient.getPatientID()));
        if (index != -1) {
            allPatients.set(index, patient);
            return savePatient(patient);
        }
        return false;
    }

    /**
     * Deletes a patient.
     * @param patientID The ID of the patient to delete
     * @return True if the patient was deleted successfully, false otherwise
     */
    public boolean deletePatient(String patientID) {
        Patient patient = retrievePatient(patientID);
        if (patient != null) {
            allPatients.remove(patient);
            return deletePatientFile(patientID);
        }
        return false;
    }

    /**
     * Retrieves all patients.
     * @return A list of all patients
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(allPatients);
    }

    // Helper methods

    private void loadAllPatients() {
        Path path = Paths.get(DIRECTORY);
        if (Files.exists(path)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*_PatientInfo.txt")) {
                for (Path file : stream) {
                    List<String> lines = Files.readAllLines(file);
                    Patient patient = parsePatient(lines.toArray(new String[0]));
                    if (patient != null) {
                        allPatients.add(patient);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Patient parsePatient(String[] data) {
        try {
            String patientID = data[0].split(": ")[1];
            String email = data[1].split(": ")[1];
            String firstName = data[2].split(": ")[1];
            String lastName = data[3].split(": ")[1];
            String dateOfBirth = data[4].split(": ")[1];
            String phoneNumber = data[5].split(": ")[1];
            String insuranceProvider = data[6].split(": ")[1];
            String emergencyContactFirstName = data[7].split(": ")[1];
            String emergencyContactLastName = data[8].split(": ")[1];
            String emergencyContactPhoneNumber = data[9].split(": ")[1];
            String medicalHistory = data[10].split(": ")[1];

            return new Patient(patientID, email, firstName, lastName, dateOfBirth, phoneNumber, insuranceProvider,
                    emergencyContactFirstName, emergencyContactLastName, emergencyContactPhoneNumber, medicalHistory);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves patient information to a text file.
     * @param patient The patient object containing information to be saved
     * @return True if the patient information was successfully saved, false otherwise
     */
    private boolean savePatient(Patient patient) {
        Path path = Paths.get(DIRECTORY);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            List<String> lines = Arrays.asList(
                    "PatientID: " + patient.getPatientID(),
                    "Email: " + patient.getEmail(),
                    "FirstName: " + patient.getFirstName(),
                    "LastName: " + patient.getLastName(),
                    "DateOfBirth: " + patient.getDateOfBirth(),
                    "PhoneNumber: " + patient.getPhoneNumber(),
                    "InsuranceProvider: " + patient.getInsuranceProvider(),
                    "EmergencyContactFirstName: " + patient.getEmergencyContactFirstName(),
                    "EmergencyContactLastName: " + patient.getEmergencyContactLastName(),
                    "EmergencyContactPhoneNumber: " + patient.getEmergencyContactPhoneNumber(),
                    "MedicalHistory: " + patient.getMedicalHistory()
            );

            Path filePath = path.resolve(patient.getPatientID() + "_PatientInfo.txt");
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes the patient information file associated with the given patient ID.
     * @param patientID The ID of the patient whose information file should be deleted
     * @return True if the patient information file was successfully deleted, false otherwise
     */
    private boolean deletePatientFile(String patientID) {
        Path filePath = Paths.get(DIRECTORY, patientID + "_PatientInfo.txt");
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
