package pl.wsb.lab.medicalclinic.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.doctor.Doctor;
import pl.wsb.lab.medicalclinic.doctor.DoctorFactory;
import pl.wsb.lab.medicalclinic.patient.Patient;
import pl.wsb.lab.medicalclinic.patient.PatientFactory;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAppointmentRepositoryTest {
    private InMemoryAppointmentRepository repository;
    private Appointment appointment;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        repository = new InMemoryAppointmentRepository();
        doctor = DoctorFactory.createDoctor(UUID.randomUUID(), "Dr. Smith", "Smith", LocalDate.now(), new ContactInfo("123456789", "dr.smith@example.com"), "12345678901", Collections.emptyList());
        patient = PatientFactory.createPatient("John", "Doe", LocalDate.now(), new ContactInfo("987654321", "john.doe@example.com"), "98765432101");
        startTime = LocalDateTime.now().minusHours(1);
        endTime = LocalDateTime.now().plusHours(1);
        appointment = new Appointment(doctor, patient, startTime, endTime);
        repository.addAppointment(appointment);
    }

    @Test
    void testAddAppointment() {
        assertEquals(1, repository.findByDoctorId(doctor.getId()).size());
    }

    @Test
    void testFindByTime() {
        List<Appointment> result = repository.findByTime(LocalDateTime.now());
        assertFalse(result.isEmpty());
        assertEquals(appointment, result.getFirst());
    }

    @Test
    void testFindByTimeNotFound() {
        List<Appointment> result = repository.findByTime(LocalDateTime.now().plusDays(1));
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByPatient() {
        List<Appointment> result = repository.findByPatient(patient);
        assertFalse(result.isEmpty());
        assertEquals(appointment, result.getFirst());
    }

    @Test
    void testFindByPatientNotFound() {
        Patient otherPatient = PatientFactory.createPatient("Jane", "Doe", LocalDate.now(), new ContactInfo("123456789", "jane.doe@example.com"), "12345678901");
        List<Appointment> result = repository.findByPatient(otherPatient);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByDoctorId() {
        List<Appointment> result = repository.findByDoctorId(doctor.getId());
        assertFalse(result.isEmpty());
        assertEquals(appointment, result.getFirst());
    }

    @Test
    void testFindByDoctorIdNotFound() {
        List<Appointment> result = repository.findByDoctorId(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    @Test
    void testRemoveAppointment() {
        repository.removeAppointment(appointment);
        assertTrue(repository.findByDoctorId(doctor.getId()).isEmpty());
    }

    @Test
    void testRemoveAppointmentNotFound() {
        Doctor otherDoctor = DoctorFactory.createDoctor(UUID.randomUUID(), "Dr. Jones", "Jones", LocalDate.now(), new ContactInfo("123456789", "dr.jones@example.com"), "12345678901", Collections.emptyList());
        Appointment otherAppointment = new Appointment(otherDoctor, patient, startTime, endTime);
        repository.removeAppointment(otherAppointment);
        assertFalse(repository.findByDoctorId(doctor.getId()).isEmpty());
    }
}