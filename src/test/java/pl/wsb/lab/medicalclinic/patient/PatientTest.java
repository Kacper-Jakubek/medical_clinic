package pl.wsb.lab.medicalclinic.patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientTest {

    private Patient patient;

    @BeforeEach
    void setUp() {
        ContactInfo contactInfo = new ContactInfo("123456789", "test@example.com");
        patient = new Patient("Jane", "Doe", LocalDate.of(1990, 2, 2), contactInfo, "98765432101");
    }

    @Test
    void testToString() {
        String expected = "Patient{firstName='Jane', lastName='Doe', dateOfBirth=1990-02-02, contactInfo=ContactInfo{phoneNumber='123456789', email='test@example.com'}, pesel='98765432101'}";
        assertEquals(expected, patient.toString());
    }
}