package pl.wsb.lab.medicalclinic.doctor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    private DoctorRepository doctorRepository;
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        doctorRepository = Mockito.mock(DoctorRepository.class);
        doctorService = new DoctorService(doctorRepository);
    }

    @Test
    void addDoctor() {
        Doctor doctor = new Doctor(UUID.randomUUID(), "John Doe", "Cardiology", LocalDate.of(1980, 1, 1), new ContactInfo("123-456-7890", "john.doe@example.com"), "12345", new ArrayList<>());
        doctorService.addDoctor(doctor);
        verify(doctorRepository, times(1)).addDoctor(doctor);
    }

    @Test
    void removeDoctor() {
        Doctor doctor = new Doctor(UUID.randomUUID(), "John Doe", "Cardiology", LocalDate.of(1980, 1, 1), new ContactInfo("123-456-7890", "john.doe@example.com"), "12345", new ArrayList<>());
        doctorService.removeDoctor(doctor);
        verify(doctorRepository, times(1)).removeDoctor(doctor);
    }

    @Test
    void findDoctorById() {
        UUID doctorId = UUID.randomUUID();
        Doctor doctor = new Doctor(doctorId, "John Doe", "Cardiology", LocalDate.of(1980, 1, 1), new ContactInfo("123-456-7890", "john.doe@example.com"), "12345", new ArrayList<>());
        when(doctorRepository.findDoctorById(doctorId)).thenReturn(Optional.of(doctor));

        Optional<Doctor> foundDoctor = doctorService.findDoctorById(doctorId);
        assertTrue(foundDoctor.isPresent());
        assertEquals(doctor, foundDoctor.get());
    }

    @Test
    void findDoctorsBySpecialty() {
        MedicalSpecialty specialty = MedicalSpecialty.CARDIOLOGY;
        Doctor doctor = new Doctor(UUID.randomUUID(), "John Doe", "Cardiology", LocalDate.of(1980, 1, 1), new ContactInfo("123-456-7890", "john.doe@example.com"), "12345", new ArrayList<>());
        doctor.addSpecialty(specialty);
        when(doctorRepository.findDoctorsBySpecialty(specialty)).thenReturn(List.of(doctor));

        List<Doctor> doctors = doctorService.findDoctorsBySpecialty(specialty);
        assertEquals(1, doctors.size());
        assertEquals(doctor, doctors.getFirst());
    }

    @Test
    void addSpecialtyToDoctor() {
        UUID doctorId = UUID.randomUUID();
        MedicalSpecialty specialty = MedicalSpecialty.CARDIOLOGY;
        Doctor doctor = new Doctor(doctorId, "John Doe", "Cardiology", LocalDate.of(1980, 1, 1), new ContactInfo("123-456-7890", "john.doe@example.com"), "12345", new ArrayList<>());
        when(doctorRepository.findDoctorById(doctorId)).thenReturn(Optional.of(doctor));

        doctorService.addSpecialtyToDoctor(doctorId, specialty);
        assertTrue(doctor.getSpecialties().contains(specialty));
        verify(doctorRepository, times(1)).addDoctor(doctor);
    }

    @Test
    void removeSpecialtyFromDoctor() {
        UUID doctorId = UUID.randomUUID();
        MedicalSpecialty specialty = MedicalSpecialty.CARDIOLOGY;
        Doctor doctor = new Doctor(doctorId, "John Doe", "Cardiology", LocalDate.of(1980, 1, 1), new ContactInfo("123-456-7890", "john.doe@example.com"), "12345", new ArrayList<>());
        doctor.addSpecialty(specialty);
        when(doctorRepository.findDoctorById(doctorId)).thenReturn(Optional.of(doctor));

        doctorService.removeSpecialtyFromDoctor(doctorId, specialty);
        assertFalse(doctor.getSpecialties().contains(specialty));
        verify(doctorRepository, times(1)).addDoctor(doctor);
    }
}