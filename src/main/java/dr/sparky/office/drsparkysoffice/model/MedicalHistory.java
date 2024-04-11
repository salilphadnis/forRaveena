package dr.sparky.office.drsparkysoffice.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Represents the medical history of a patient.
 */
public class MedicalHistory implements Serializable {

    private String patientId;
    private final int historyId;
    private LocalDateTime date;
    private String allergies;
    private String pharmacy;
    private String prescription;
    private Vitals vitals;
    private boolean isVisitCompleted;

    /**
     * Constructor to create a new medical history entry for a patient.
     * @param patientId The ID of the patient
     * @param vitals The vital signs recorded during the visit
     */
    public MedicalHistory(String patientId, Vitals vitals) {
        this.patientId = patientId;
        this.vitals = vitals;
        this.date = LocalDateTime.now(); // Initialize with current date

        Random rand = new Random();
        historyId = rand.nextInt(100000);
    }

    public int getHistoryId() {
        return historyId;
    }

    /**
     * Checks if the visit is completed.
     * @return True if the visit is completed, false otherwise
     */
    public boolean isVisitCompleted() {
        return isVisitCompleted;
    }

    /**
     * Sets whether the visit is completed.
     * @param visitCompleted True if the visit is completed, false otherwise
     */
    public void setVisitCompleted(boolean visitCompleted) {
        isVisitCompleted = visitCompleted;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }


    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public Vitals getVitals() {
        return vitals;
    }

    public void setVitals(Vitals vitals) {
        this.vitals = vitals;
    }

    @Override
    public String toString() {
        return "MedicalHistory{" +
                "date=" + date +
                ", allergies='" + allergies + '\'' +
                ", pharmacy='" + pharmacy + '\'' +
                ", prescription='" + prescription + '\'' +
                ", vitals=" + vitals +
                '}';
    }
}
