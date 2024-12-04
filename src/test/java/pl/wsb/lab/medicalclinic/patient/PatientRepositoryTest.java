package pl.wsb.lab.medicalclinic.patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PatientRepositoryTest {

    private PatientRepository patientRepository;
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;

    @BeforeEach
    void setUp() {
        patientRepository = new PatientRepository();
        patient1 = new Patient("John", "Doe", LocalDate.of(1980, 1, 1), new ContactInfo("123456789", "john.doe@example.com"), "12345678901");
        patient2 = new Patient("Jane", "Doe", LocalDate.of(1985, 2, 2), new ContactInfo("987654321", "jane.doe@example.com"), "12345678902");
        patient3 = new Patient("Alice", "Smith", LocalDate.of(1990, 3, 3), new ContactInfo("555555555", "alice.smith@example.com"), "12345678903");

        patientRepository.addPatient(patient1);
        patientRepository.addPatient(patient2);
        patientRepository.addPatient(patient3);
    }

    @Test
    void testAddPatient() {
        Patient newPatient = new Patient("Bob", "Brown", LocalDate.of(1975, 4, 4), new ContactInfo("444444444", "bob.brown@example.com"), "12345678904");
        patientRepository.addPatient(newPatient);

        Optional<Patient> retrievedPatient = patientRepository.findByPesel("12345678904");
        assertTrue(retrievedPatient.isPresent());
        assertEquals(newPatient, retrievedPatient.get());
    }

    @Test
    void testFindByPesel() {
        Optional<Patient> retrievedPatient = patientRepository.findByPesel("12345678901");
        assertTrue(retrievedPatient.isPresent());
        assertEquals(patient1, retrievedPatient.get());

        Optional<Patient> nonExistentPatient = patientRepository.findByPesel("00000000000");
        assertFalse(nonExistentPatient.isPresent());
    }

    @Test
    void testFindByLastName() {
        List<Patient> doePatients = patientRepository.findByLastName("Doe");
        assertEquals(2, doePatients.size());
        assertTrue(doePatients.contains(patient1));
        assertTrue(doePatients.contains(patient2));

        List<Patient> smithPatients = patientRepository.findByLastName("Smith");
        assertEquals(1, smithPatients.size());
        assertTrue(smithPatients.contains(patient3));

        List<Patient> nonExistentPatients = patientRepository.findByLastName("NonExistent");
        assertTrue(nonExistentPatients.isEmpty());
    }
}