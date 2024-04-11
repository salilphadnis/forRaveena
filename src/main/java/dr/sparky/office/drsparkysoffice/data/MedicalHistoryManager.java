package dr.sparky.office.drsparkysoffice.data;

import dr.sparky.office.drsparkysoffice.model.MedicalHistory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the storage and retrieval of medical histories for patients.
 */
public class MedicalHistoryManager {

    // Path to the medical history file
    private static final String MEDICAL_HISTORY_FILE = "database/medicalHistory.bin";

    // Map to store medical histories with patient IDs as keys
    private Map<String, List<MedicalHistory>> medicalHistories;

    /**
     * Constructs a MedicalHistoryManager object and loads medical histories from the file.
     */
    public MedicalHistoryManager() {
        loadMedicalHistories();
    }

    /**
     * Loads medical histories from the file into the medical histories map.
     * If the file does not exist, initializes an empty map.
     */
    private void loadMedicalHistories() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MEDICAL_HISTORY_FILE))) {
            medicalHistories = (Map<String, List<MedicalHistory>>) ois.readObject();
        } catch (FileNotFoundException e) {
            medicalHistories = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load medical histories", e);
        }
    }

    /**
     * Saves the medical histories map to the file.
     */
    public void saveMedicalHistories() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MEDICAL_HISTORY_FILE))) {
            oos.writeObject(medicalHistories);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save medical histories", e);
        }
    }

    /**
     * Adds a medical history for the specified patient.
     *
     * @param patientId The ID of the patient
     * @param history   The medical history to be added
     */
    public void addMedicalHistory(String patientId, MedicalHistory history) {
        List<MedicalHistory> patientHistories = medicalHistories.getOrDefault(patientId, new ArrayList<>());
        patientHistories.add(history);
        medicalHistories.put(patientId, patientHistories);
        saveMedicalHistories();
    }

    /**
     * Retrieves the medical histories for the specified patient.
     *
     * @param patientId The ID of the patient
     * @return The list of medical histories for the specified patient
     */
    public List<MedicalHistory> getMedicalHistories(String patientId) {
        return medicalHistories.getOrDefault(patientId, new ArrayList<>());
    }

    /**
     * Updates a medical history for the specified patient.
     *
     * @param patientId      The ID of the patient
     * @param updatedHistory The updated medical history
     * @return True if the medical history was successfully updated, false otherwise
     */
    public boolean updateMedicalHistory(String patientId, int hId, MedicalHistory updatedHistory) {
        if (!medicalHistories.containsKey(patientId)) {
            return false; // No medical histories for this patient
        }

        List<MedicalHistory> patientHistories = medicalHistories.get(patientId);
        for (int i = 0; i < patientHistories.size(); i++) {
            if (patientHistories.get(i).getHistoryId() == hId) {
                patientHistories.set(i, updatedHistory);
            }
        }

        saveMedicalHistories();
        return true;
    }

    /**
     * Deletes a medical history for the specified patient.
     *
     * @param patientId The ID of the patient
     * @param historyId The ID of the medical history to be deleted
     * @return True if the medical history was successfully deleted, false otherwise
     */
    public boolean deleteMedicalHistory(String patientId, String historyId) {
        if (!medicalHistories.containsKey(patientId)) {
            return false; // No medical histories for this patient
        }

        List<MedicalHistory> patientHistories = medicalHistories.get(patientId);
        patientHistories.removeIf(h -> h.getPatientId().equals(historyId));
        if (patientHistories.isEmpty()) {
            medicalHistories.remove(patientId);
        }
        saveMedicalHistories();
        return true;
    }
}
