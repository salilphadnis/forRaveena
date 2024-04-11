package dr.sparky.office.drsparkysoffice.test;

import dr.sparky.office.drsparkysoffice.data.MedicalHistoryManager;
import dr.sparky.office.drsparkysoffice.model.MedicalHistory;
import dr.sparky.office.drsparkysoffice.model.Vitals;

import java.time.LocalDateTime;
import java.util.List;

public class MedicalHistoryManagerTest {

    public static void main(String[] args) {
        MedicalHistoryManager historyManager = new MedicalHistoryManager();

        // Test adding a medical history
        Vitals vitals1 = new Vitals(70, 175, 36.5, "120/80");
        MedicalHistory history1 = new MedicalHistory("1", vitals1); // Assuming patientId is 1
        historyManager.addMedicalHistory("1", history1);
        System.out.println("Medical History 1 added");

        // Test retrieving medical histories
        List<MedicalHistory> retrievedHistories = historyManager.getMedicalHistories("1");
        System.out.println("Retrieved histories for patient 1: " + retrievedHistories);

        // Test updating a medical history
        // Assuming MedicalHistory class has a method to update vitals and a unique identifier
        if (!retrievedHistories.isEmpty()) {
            MedicalHistory historyToUpdate = retrievedHistories.get(0);
            Vitals updatedVitals = new Vitals(72, 175, 37.0, "130/85");
            historyToUpdate.setVitals(updatedVitals);
            boolean updated = historyManager.updateMedicalHistory("1", historyToUpdate.getHistoryId(), historyToUpdate);
            System.out.println("Medical history updated: " + updated);
        }

        // Test deleting a medical history
        // Assuming MedicalHistory class has a unique identifier, e.g., historyId
        boolean deleted = historyManager.deleteMedicalHistory("1", history1.getPatientId());
        System.out.println("Medical history deleted: " + deleted);
    }
}

