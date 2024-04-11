package dr.sparky.office.drsparkysoffice.test;

import dr.sparky.office.drsparkysoffice.data.PatientManager;
import dr.sparky.office.drsparkysoffice.model.Patient;
import java.util.List;

public class PatientManagerTest {

    public static void main(String[] args) {
        PatientManager patientManager = new PatientManager();

        // Test adding a patient
        Patient patient1 = new Patient("ID001", "test@example.com", "John", "Doe",
                "1990-01-01", "1234567890", "Insurance XYZ", "Jane", "Doe", "9876543210", "None");
        boolean added1 = patientManager.addPatient(patient1);
        System.out.println("Patient 1 added: " + added1);

        // Test retrieving a patient
        Patient retrievedPatient = patientManager.retrievePatient("ID001");
        System.out.println("Retrieved patient: " + retrievedPatient);

        // Test updating a patient
        if (retrievedPatient != null) {
            retrievedPatient.setFirstName("John Updated");
            retrievedPatient.setPhoneNumber("5555555555");
            boolean updated = patientManager.updatePatient(retrievedPatient);
            System.out.println("Patient updated: " + updated);
        }

        // Test deleting a patient
//        boolean deleted = patientManager.deletePatient("ID001");
//        System.out.println("Patient deleted: " + deleted);

        // Test getting all patients
        List<Patient> allPatients = patientManager.getAllPatients();
        System.out.println("All patients: " + allPatients);
    }
}

