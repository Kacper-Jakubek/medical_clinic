package pl.wsb.lab.medicalclinic.patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    private PatientRepository patientRepository;
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        patientRepository = Mockito.mock(PatientRepository.class);
        patientService = new PatientService(patientRepository);
    }

    @Test
    public void testAddPatient() {
        Patient patient = new Patient("John", "Doe", LocalDate.of(1990, 1, 1), new ContactInfo("john.doe@example.com", "123456789"), "12345678901");
        patientService.addPatient(patient);
        verify(patientRepository, times(1)).addPatient(patient);
    }

    @Test
    public void testRemovePatient() {
        Patient patient = new Patient("John", "Doe", LocalDate.of(1990, 1, 1), new ContactInfo("john.doe@example.com", "123456789"), "12345678901");
        patientService.removePatient(patient);
        verify(patientRepository, times(1)).removePatient(patient);
    }

    @Test
    public void testSearchByLastName() {
        String lastName = "Doe";
        List<Patient> expectedPatients = List.of(new Patient("John", "Doe", LocalDate.of(1990, 1, 1), new ContactInfo("john.doe@example.com", "123456789"), "12345678901"));
        when(patientRepository.findByLastName(lastName)).thenReturn(expectedPatients);

        List<Patient> actualPatients = patientService.searchByLastName(lastName);
        assertEquals(expectedPatients, actualPatients);
    }

    @Test
    public void testSearchByPesel() {
        String pesel = "12345678901";
        Patient expectedPatient = new Patient("John", "Doe", LocalDate.of(1990, 1, 1), new ContactInfo("john.doe@example.com", "123456789"), pesel);
        when(patientRepository.findByPesel(pesel)).thenReturn(Optional.of(expectedPatient));

        Optional<Patient> actualPatient = patientService.searchByPesel(pesel);
        assertTrue(actualPatient.isPresent());
        assertEquals(expectedPatient, actualPatient.get());
    }
}