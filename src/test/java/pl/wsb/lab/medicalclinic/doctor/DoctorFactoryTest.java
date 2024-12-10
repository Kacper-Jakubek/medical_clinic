package pl.wsb.lab.medicalclinic.doctor;

import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DoctorFactoryTest {

    @Test
    void testCreateDoctorWithValidData() {
        UUID doctorId = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1980, 1, 1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "12345678901";
        Doctor doctor = DoctorFactory.createDoctor(doctorId, "John", "Doe", dob, contactInfo, pesel, Collections.emptyList());

        assertNotNull(doctor);
        assertEquals(doctorId, doctor.getId());
        assertEquals("John", doctor.getFirstName());
        assertEquals("Doe", doctor.getLastName());
        assertEquals(dob, doctor.getDateOfBirth());
        assertEquals(contactInfo, doctor.getContactInfo());
        assertEquals(pesel, doctor.getPesel());
        assertTrue(doctor.getSpecialties().isEmpty());
    }

    @Test
    void testCreateDoctorWithNullFirstName() {
        UUID doctorId = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1980, 1, 1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "12345678901";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor(doctorId, null, "Doe", dob, contactInfo, pesel, Collections.emptyList()));
        assertEquals("First name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithNullLastName() {
        UUID doctorId = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1980, 1, 1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "12345678901";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor(doctorId, "John", null, dob, contactInfo, pesel, Collections.emptyList()));
        assertEquals("Last name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithNullDateOfBirth() {
        UUID doctorId = UUID.randomUUID();
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "12345678901";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor(doctorId, "John", "Doe", null, contactInfo, pesel, Collections.emptyList()));
        assertEquals("Date of birth cannot be null or in the future.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithNullContactInfo() {
        UUID doctorId = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1980, 1, 1);
        String pesel = "12345678901";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor(doctorId, "John", "Doe", dob, null, pesel, Collections.emptyList()));
        assertEquals("Phone number cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithNullPesel() {
        UUID doctorId = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1980, 1, 1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor(doctorId, "John", "Doe", dob, contactInfo, null, Collections.emptyList()));
        assertEquals("Provided PESEL is not valid.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithInvalidPesel() {
        UUID doctorId = UUID.randomUUID();
        LocalDate dob = LocalDate.of(1980, 1, 1);
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        String pesel = "1234567890"; // Invalid PESEL

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor(doctorId, "John", "Doe", dob, contactInfo, pesel, Collections.emptyList()));
        assertEquals("Provided PESEL is not valid.", exception.getMessage());
    }

    @Test
    void testCreateDoctorWithValidStringData() {
        String firstName = "John";
        String lastName = "Doe";
        String dateOfBirth = "1980-01-01";
        String phoneNumber = "123456789";
        String email = "test@example.com";
        String pesel = "12345678901";
        String medicalSpecialties = "";

        Doctor doctor = DoctorFactory.createDoctor(firstName, lastName, dateOfBirth, phoneNumber, email, pesel, medicalSpecialties);

        assertNotNull(doctor);
        assertEquals(firstName, doctor.getFirstName());
        assertEquals(lastName, doctor.getLastName());
        assertEquals(LocalDate.of(1980, 1, 1), doctor.getDateOfBirth());
        assertEquals(new ContactInfo(phoneNumber, email), doctor.getContactInfo());
        assertEquals(pesel, doctor.getPesel());
        assertTrue(doctor.getSpecialties().isEmpty());
    }

    @Test
    void testCreateDoctorWithInvalidDateOfBirth() {
        String firstName = "John";
        String lastName = "Doe";
        String dateOfBirth = "invalid-date";
        String phoneNumber = "123456789";
        String email = "test@example.com";
        String pesel = "12345678901";
        String medicalSpecialties = "";

        assertThrows(IllegalArgumentException.class, () ->
                DoctorFactory.createDoctor(firstName, lastName, dateOfBirth, phoneNumber, email, pesel, medicalSpecialties));
    }

}