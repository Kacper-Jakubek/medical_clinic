package pl.wsb.lab.medicalclinic.patient;

import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PatientFactoryTest {

    @Test
    void testCreatePatientWithValidData() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        Patient patient = PatientFactory.createPatient("John", "Doe", LocalDate.of(1990, 1, 1), contactInfo, "12345678901");
        assertNotNull(patient);
    }

    @Test
    void testCreatePatientWithInvalidFirstName() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PatientFactory.createPatient("", "Doe", LocalDate.of(1990, 1, 1), contactInfo, "12345678901"));
        assertEquals("First name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreatePatientWithInvalidLastName() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PatientFactory.createPatient("John", "", LocalDate.of(1990, 1, 1), contactInfo, "12345678901"));
        assertEquals("Last name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreatePatientWithInvalidPesel() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PatientFactory.createPatient("John", "Doe", LocalDate.of(1990, 1, 1), contactInfo, "123"));
        assertEquals("Provided PESEL is not valid.", exception.getMessage());
    }

    @Test
    void testCreatePatientWithFutureDateOfBirth() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PatientFactory.createPatient("John", "Doe", LocalDate.now().plusDays(1), contactInfo, "12345678901"));
        assertEquals("Date of birth cannot be null or in the future.", exception.getMessage());
    }

    @Test
    void testCreatePatientWithInvalidPhoneNumber() {
        ContactInfo contactInfo = new ContactInfo("", "test@example.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PatientFactory.createPatient("John", "Doe", LocalDate.of(1990, 1, 1), contactInfo, "12345678901"));
        assertEquals("Phone number cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreatePatientWithValidStringDate() {
        Patient patient = PatientFactory.createPatient("John", "Doe", "1990-01-01", "123456789", "test@example.com", "12345678901");
        assertNotNull(patient);
    }

    @Test
    void testCreatePatientWithInvalidStringDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                PatientFactory.createPatient("John", "Doe", "01-13-1990", "123456789", "test@example.com", "12345678901"));
        assertEquals("Provided date of birth is not in the correct format. Accepted formats are: \"yyyy-MM-dd\", \"yyyy.MM.dd\", \"dd.MM.yyyy\", \"dd-MM-yyyy\"", exception.getMessage());
    }
}