package dr.sparky.office.drsparkysoffice.model;
import java.io.Serializable;

/**
 * Represents a user account in the system.
 */
public class UserAccount implements Serializable {

    private String email;
    private String password;
    private UserType type;
    private Patient patient;

    /**
     * Constructor for creating a user account for a doctor or nurse.
     * @param email The email address of the user
     * @param password The password of the user
     * @param type The type of user (doctor or nurse)
     */
    public UserAccount(String email, String password, UserType type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    /**
     * Constructor for creating a user account for a patient.
     * @param email The email address of the user
     * @param password The password of the user
     * @param type The type of user (patient)
     * @param patient The patient associated with this account
     */
    public UserAccount(String email, String password, UserType type, Patient patient) {
        this.email = email;
        this.password = password;
        this.type = type;
        this.patient = patient;
    }

    /**
     * Checks if the user account belongs to a patient.
     * @return True if the user is a patient, false otherwise
     */
    public boolean isPatient(){
        return  patient != null;
    }

    /**
     * Checks if the user account belongs to a nurse.
     * @return True if the user is a nurse, false otherwise
     */
    public boolean isNurse(){
        return  type.equals(UserType.NURSE);
    }

    /**
     * Checks if the user account belongs to a doctor.
     * @return True if the user is a doctor, false otherwise
     */
    public boolean isDoctor(){
        return  type.equals(UserType.DOCTOR);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return email;
    }
}
