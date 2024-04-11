package dr.sparky.office.drsparkysoffice.model;

import java.io.Serializable;

/**
 * Represents the vital signs of a patient, including weight, height, temperature, and blood pressure.
 */
public class Vitals implements Serializable {

    // Attributes

    /** The weight of the patient. */
    private int weight;

    /** The height of the patient. */
    private double height;

    /** The temperature of the patient. */
    private double temperature;

    /** The blood pressure of the patient. */
    private String bloodPressure;

    // Constructor

    /**
     * Constructs a new Vitals object with the specified weight, height, temperature, and blood pressure.
     * @param weight The weight of the patient.
     * @param height The height of the patient.
     * @param temperature The temperature of the patient.
     * @param bloodPressure The blood pressure of the patient.
     */
    public Vitals(int weight, double height, double temperature, String bloodPressure) {
        this.weight = weight;
        this.height = height;
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
    }

    // Getter and setter methods

    /**
     * Returns the weight of the patient.
     * @return The weight of the patient.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the patient.
     * @param weight The weight of the patient to set.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Returns the height of the patient.
     * @return The height of the patient.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the patient.
     * @param height The height of the patient to set.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Returns the temperature of the patient.
     * @return The temperature of the patient.
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Sets the temperature of the patient.
     * @param temperature The temperature of the patient to set.
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Returns the blood pressure of the patient.
     * @return The blood pressure of the patient.
     */
    public String getBloodPressure() {
        return bloodPressure;
    }

    /**
     * Sets the blood pressure of the patient.
     * @param bloodPressure The blood pressure of the patient to set.
     */
    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    // toString method

    /**
     * Returns a string representation of the Vitals object.
     * @return A string representation of the Vitals object.
     */
    @Override
    public String toString() {
        return "Vitals{" +
                "weight=" + weight +
                ", height=" + height +
                ", temperature=" + temperature +
                ", bloodPressure='" + bloodPressure + '\'' +
                '}';
    }
}
