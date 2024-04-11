package dr.sparky.office.drsparkysoffice.model;

import java.io.Serializable;

/**
 * Enumeration representing the types of users in the system.
 */
public enum UserType implements Serializable {

    /** Represents a patient user type. */
    PATIENT,

    /** Represents a nurse user type. */
    NURSE,

    /** Represents a doctor user type. */
    DOCTOR
}
