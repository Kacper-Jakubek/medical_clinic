package pl.wsb.lab.medicalclinic.doctor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDoctorRepositoryTest {

    private InMemoryDoctorRepository repository;
    private Doctor doctor;
    private UUID doctorId;

    @BeforeEach
    void setUp() {
        repository = new InMemoryDoctorRepository();
        doctorId = UUID.randomUUID();
        doctor = new Doctor(doctorId, "John", "Doe", LocalDate.of(1980, 1, 1), new ContactInfo("123456789", "test@example.com"), "12345678901", Collections.emptyList());
    }

    @Test
    void testAddDoctor() {
        repository.addDoctor(doctor);
        Optional<Doctor> foundDoctor = repository.findDoctorById(doctorId);
        assertTrue(foundDoctor.isPresent());
        assertEquals(doctor, foundDoctor.get());
    }

    @Test
    void testRemoveDoctor() {
        repository.addDoctor(doctor);
        repository.removeDoctor(doctor);
        Optional<Doctor> foundDoctor = repository.findDoctorById(doctorId);
        assertFalse(foundDoctor.isPresent());
    }

    @Test
    void testFindDoctorById() {
        repository.addDoctor(doctor);
        Optional<Doctor> foundDoctor = repository.findDoctorById(doctorId);
        assertTrue(foundDoctor.isPresent());
        assertEquals(doctor, foundDoctor.get());
    }

    @Test
    void testFindDoctorByIdNotFound() {
        Optional<Doctor> foundDoctor = repository.findDoctorById(UUID.randomUUID());
        assertFalse(foundDoctor.isPresent());
    }

    @Test
    void testFindDoctorsBySpecialty() {
        MedicalSpecialty specialty = MedicalSpecialty.valueOf("CARDIOLOGY");
        Doctor doctorWithSpecialty = new Doctor(UUID.randomUUID(), "Jane", "Doe", LocalDate.of(1985, 5, 5), new ContactInfo("987654321", "jane@example.com"), "98765432101", List.of(specialty));
        repository.addDoctor(doctorWithSpecialty);
        List<Doctor> doctors = repository.findDoctorsBySpecialty(specialty);
        assertEquals(1, doctors.size());
        assertEquals(doctorWithSpecialty, doctors.get(0));
    }

    @Test
    void testFindDoctorsBySpecialtyNotFound() {
        MedicalSpecialty specialty = MedicalSpecialty.valueOf("NEUROLOGY");
        List<Doctor> doctors = repository.findDoctorsBySpecialty(specialty);
        assertTrue(doctors.isEmpty());
    }
}