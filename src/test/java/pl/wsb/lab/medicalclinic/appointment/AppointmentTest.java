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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        doctor = DoctorFactory.createDoctor(UUID.randomUUID(), "Dr. Smith", "Smith", LocalDate.now(), new ContactInfo("123456789", "dr.smith@example.com"), "12345678901", Collections.emptyList());
        patient = PatientFactory.createPatient("John", "Doe", LocalDate.now(), new ContactInfo("987654321", "john.doe@example.com"), "98765432101");
        startTime = LocalDateTime.now().minusHours(1);
        endTime = LocalDateTime.now().plusHours(1);
        appointment = new Appointment(doctor, patient, startTime, endTime);
    }

    @Test
    void testCreateAppointment() {
        assertNotNull(appointment);
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(patient, appointment.getPatient());
        assertEquals(startTime, appointment.getStartTime());
        assertEquals(endTime, appointment.getEndTime());
    }

    @Test
    void testSetDoctor() {
        Doctor newDoctor = DoctorFactory.createDoctor(UUID.randomUUID(), "Dr. Jones", "Jones", LocalDate.now(), new ContactInfo("123456789", "dr.jones@example.com"), "12345678901", Collections.emptyList());
        appointment.setDoctor(newDoctor);
        assertEquals(newDoctor, appointment.getDoctor());
    }

    @Test
    void testSetStartTime() {
        LocalDateTime newStartTime = LocalDateTime.now().plusHours(2);
        appointment.setStartTime(newStartTime);
        assertEquals(newStartTime, appointment.getStartTime());
    }

    @Test
    void testSetEndTime() {
        LocalDateTime newEndTime = LocalDateTime.now().plusHours(3);
        appointment.setEndTime(newEndTime);
        assertEquals(newEndTime, appointment.getEndTime());
    }

    @Test
    void testOverlaps() {
        Appointment overlappingAppointment = new Appointment(doctor, patient, startTime.plusMinutes(30), endTime.plusMinutes(30));
        assertTrue(appointment.overlaps(overlappingAppointment));
    }

    @Test
    void testDoesNotOverlap() {
        Appointment nonOverlappingAppointment = new Appointment(doctor, patient, endTime.plusMinutes(1), endTime.plusMinutes(31));
        assertFalse(appointment.overlaps(nonOverlappingAppointment));
    }

    @Test
    void testToString() {
        String expected = "Appointment{doctor=" + doctor.toString() + ", patient=" + patient.toString() + ", startTime=" + startTime.toString() + ", endTime=" + endTime.toString() + "}";
        assertEquals(expected, appointment.toString());
    }
}