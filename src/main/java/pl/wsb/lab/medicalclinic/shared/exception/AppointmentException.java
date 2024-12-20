package pl.wsb.lab.medicalclinic.shared.exception;

public class AppointmentException extends Exception {
    public static final String DOCTOR_NOT_AVAILABLE = "Doctor is not available at this time.";
    public static final String APPOINTMENT_OVERLAP = "Appointment overlaps with existing appointment.";

    public AppointmentException(String message) {
        super(message);
    }
}