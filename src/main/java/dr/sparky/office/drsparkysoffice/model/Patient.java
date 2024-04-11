package dr.sparky.office.drsparkysoffice.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a patient in the system.
 */
public class Patient implements Serializable {

    private String patientID;
    private String email;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    private String insuranceProvider;
    private String emergencyContactFirstName;
    private String emergencyContactLastName;
    private String emergencyContactPhoneNumber;
    private String medicalHistory;

    /**
     * Constructor for creating a patient without medical history.
     * @param patientID The ID of the patient
     * @param email The email of the patient
     * @param firstName The first name of the patient
     * @param lastName The last name of the patient
     * @param dateOfBirth The date of birth of the patient
     * @param phoneNumber The phone number of the patient
     * @param insuranceProvider The insurance provider of the patient
     * @param emergencyContactFirstName The first name of the emergency contact person
     * @param emergencyContactLastName The last name of the emergency contact person
     * @param emergencyContactPhoneNumber The phone number of the emergency contact person
     */
    public Patient(String patientID, String email, String firstName, String lastName,
                   String dateOfBirth, String phoneNumber, String insuranceProvider,
                   String emergencyContactFirstName, String emergencyContactLastName,
                   String emergencyContactPhoneNumber) {
        this.patientID = patientID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insuranceProvider = insuranceProvider;
        this.emergencyContactFirstName = emergencyContactFirstName;
        this.emergencyContactLastName = emergencyContactLastName;
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
    }

    /**
     * Constructor for creating a patient with medical history.
     * @param patientID The ID of the patient
     * @param email The email of the patient
     * @param firstName The first name of the patient
     * @param lastName The last name of the patient
     * @param dateOfBirth The date of birth of the patient
     * @param phoneNumber The phone number of the patient
     * @param insuranceProvider The insurance provider of the patient
     * @param emergencyContactFirstName The first name of the emergency contact person
     * @param emergencyContactLastName The last name of the emergency contact person
     * @param emergencyContactPhoneNumber The phone number of the emergency contact person
     * @param medicalHistory The medical history of the patient
     */
    public Patient(String patientID, String email, String firstName, String lastName,
                   String dateOfBirth, String phoneNumber, String insuranceProvider,
                   String emergencyContactFirstName, String emergencyContactLastName,
                   String emergencyContactPhoneNumber, String medicalHistory) {
        this.patientID = patientID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insuranceProvider = insuranceProvider;
        this.emergencyContactFirstName = emergencyContactFirstName;
        this.emergencyContactLastName = emergencyContactLastName;
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
        this.medicalHistory = medicalHistory;
    }

    /**
     * Updates the profile of the patient.
     * @param email The email of the patient
     * @param firstName The first name of the patient
     * @param lastName The last name of the patient
     * @param dateOfBirth The date of birth of the patient
     * @param phoneNumber The phone number of the patient
     * @param insuranceProvider The insurance provider of the patient
     * @param emergencyContactFirstName The first name of the emergency contact person
     * @param emergencyContactLastName The last name of the emergency contact person
     * @param emergencyContactPhoneNumber The phone number of the emergency contact person
     * @param medicalHistory The medical history of the patient
     */
    public void updateProfile(String email, String firstName, String lastName,
                              String dateOfBirth, String phoneNumber, String insuranceProvider,
                              String emergencyContactFirstName, String emergencyContactLastName,
                              String emergencyContactPhoneNumber, String medicalHistory) {
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setPhoneNumber(phoneNumber);
        setInsuranceProvider(insuranceProvider);
        setEmergencyContactFirstName(emergencyContactFirstName);
        setEmergencyContactLastName(emergencyContactLastName);
        setEmergencyContactPhoneNumber(emergencyContactPhoneNumber);
        setMedicalHistory(medicalHistory);
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getEmergencyContactFirstName() {
        return emergencyContactFirstName;
    }

    public void setEmergencyContactFirstName(String emergencyContactFirstName) {
        this.emergencyContactFirstName = emergencyContactFirstName;
    }

    public String getEmergencyContactLastName() {
        return emergencyContactLastName;
    }

    public void setEmergencyContactLastName(String emergencyContactLastName) {
        this.emergencyContactLastName = emergencyContactLastName;
    }

    public String getEmergencyContactPhoneNumber() {
        return emergencyContactPhoneNumber;
    }

    public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patientID.equals(patient.patientID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientID);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientID='" + patientID + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", insuranceProvider='" + insuranceProvider + '\'' +
                ", emergencyContactFirstName='" + emergencyContactFirstName + '\'' +
                ", emergencyContactLastName='" + emergencyContactLastName + '\'' +
                ", emergencyContactPhoneNumber='" + emergencyContactPhoneNumber + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                '}';
    }
}
