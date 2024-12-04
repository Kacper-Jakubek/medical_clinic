package pl.wsb.lab.medicalclinic.doctor;

import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DoctorFactoryTest {

    @Test
    void testCreateDoctorWithValidData() {
        LocalDate dob = LocalDate.of(1980, 1, 1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "12345678901";
        Doctor doctor = DoctorFactory.createDoctor("John", "Doe", dob, contactInfo, pesel, Collections.emptyList());

        assertNotNull(doctor);
        assertEquals("John", doctor.getFirstName());
        assertEquals("Doe", doctor.getLastName());
        assertEquals(dob, doctor.getDateOfBirth());
        assertEquals(contactInfo, doctor.getContactInfo());
        assertEquals(pesel, doctor.getPesel());
        assertTrue(doctor.getSpecialties().isEmpty());
    }

    @Test
    void testCreateDoctorWithStringDateOfBirth() {
        String dateOfBirth = "1980-01-01";
        String phoneNumber = "123456789";
        String email = "test@example.com";
        String pesel = "12345678901";
        String medicalSpecialties = "Cardiology";

        Doctor doctor = DoctorFactory.createDoctor("John", "Doe", dateOfBirth, phoneNumber, email, pesel, medicalSpecialties);

        assertNotNull(doctor);
        assertEquals("John", doctor.getFirstName());
        assertEquals("Doe", doctor.getLastName());
        assertEquals(LocalDate.of(1980, 1, 1), doctor.getDateOfBirth());
        assertEquals(phoneNumber, doctor.getContactInfo().getPhoneNumber());
        assertEquals(email, doctor.getContactInfo().getEmail());
        assertEquals(pesel, doctor.getPesel());
        assertFalse(doctor.getSpecialties().isEmpty());
    }

    @Test
    void testCreateDoctorWithInvalidFirstName() {
        LocalDate dob = LocalDate.of(1980, 1, 1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "12345678901";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor("", "Doe", dob, contactInfo, pesel, Collections.emptyList()));
        assertEquals("First name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithInvalidLastName() {
        LocalDate dob = LocalDate.of(1980, 1, 1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "12345678901";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor("John", "", dob, contactInfo, pesel, Collections.emptyList()));
        assertEquals("Last name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithInvalidPesel() {
        LocalDate dob = LocalDate.of(1980, 1, 1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "1234567890"; // Invalid PESEL

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor("John", "Doe", dob, contactInfo, pesel, Collections.emptyList()));
        assertEquals("Provided PESEL is not valid.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithFutureDateOfBirth() {
        LocalDate dob = LocalDate.now().plusDays(1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "12345678901";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor("John", "Doe", dob, contactInfo, pesel, Collections.emptyList()));
        assertEquals("Date of birth cannot be null or in the future.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithInvalidPhoneNumber() {
        String dateOfBirth = "1980-01-01";
        String phoneNumber = "";
        String email = "test@example.com";
        String pesel = "12345678901";
        String medicalSpecialties = "Cardiology";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor("John", "Doe", dateOfBirth, phoneNumber, email, pesel, medicalSpecialties));
        assertEquals("Phone number cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithInvalidStringDate() {
        String dateOfBirth = "01-13-1980"; // Invalid date format
        String phoneNumber = "123456789";
        String email = "test@example.com";
        String pesel = "12345678901";
        String medicalSpecialties = "Cardiology";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor("John", "Doe", dateOfBirth, phoneNumber, email, pesel, medicalSpecialties));
        assertEquals("Provided date of birth is not in the correct format. Accepted formats are: \"yyyy-MM-dd\", \"yyyy.MM.dd\", \"dd.MM.yyyy\", \"dd-MM-yyyy\"", exception.getMessage());
    }
}