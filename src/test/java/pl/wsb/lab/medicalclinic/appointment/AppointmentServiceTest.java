package pl.wsb.lab.medicalclinic.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.doctor.Doctor;
import pl.wsb.lab.medicalclinic.doctor.DoctorFactory;
import pl.wsb.lab.medicalclinic.patient.Patient;
import pl.wsb.lab.medicalclinic.patient.PatientFactory;
import pl.wsb.lab.medicalclinic.schedule.Schedule;
import pl.wsb.lab.medicalclinic.schedule.ScheduleService;
import pl.wsb.lab.medicalclinic.schedule.WorkingHours;
import pl.wsb.lab.medicalclinic.shared.exception.AppointmentException;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {
    private AppointmentRepository appointmentRepository;
    private ScheduleService scheduleService;
    private AppointmentService appointmentService;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        appointmentRepository = mock(AppointmentRepository.class);
        scheduleService = mock(ScheduleService.class);
        appointmentService = new AppointmentService(appointmentRepository, scheduleService);

        doctor = DoctorFactory.createDoctor(UUID.randomUUID(), "Dr. Smith", "Smith", LocalDate.now(), new ContactInfo("123456789", "dr.smith@example.com"), "12345678901", Collections.emptyList());
        patient = PatientFactory.createPatient("John", "Doe", LocalDate.now(), new ContactInfo("987654321", "john.doe@example.com"), "98765432101");
        startTime = LocalDateTime.now().plusHours(1);
        endTime = startTime.plusMinutes(30);
    }

    @Test
    void testCreateAppointmentSuccess() throws AppointmentException {
        when(scheduleService.isDoctorAvailableForSchedule(doctor.getId(), startTime, endTime)).thenReturn(true);

        appointmentService.createAppointment(patient, doctor, startTime, endTime);

        verify(appointmentRepository, times(1)).addAppointment(any(Appointment.class));
    }

    @Test
    void testCreateAppointmentDoctorNotAvailable() {
        when(scheduleService.isDoctorAvailableForSchedule(doctor.getId(), startTime, endTime)).thenReturn(false);

        AppointmentException exception = assertThrows(AppointmentException.class, () -> appointmentService.createAppointment(patient, doctor, startTime, endTime));

        assertEquals("Doctor is not available at this time.", exception.getMessage());
        verify(appointmentRepository, never()).addAppointment(any(Appointment.class));
    }

    @Test
    void testCancelAppointment() {
        Appointment appointment = new Appointment(doctor, patient, startTime, endTime);

        appointmentService.cancelAppointment(appointment);

        verify(appointmentRepository, times(1)).removeAppointment(appointment);
    }

    @Test
    void testRescheduleAppointmentSuccess() throws AppointmentException {
        Appointment appointment = new Appointment(doctor, patient, startTime, endTime);
        LocalDateTime newStartTime = startTime.plusDays(1);
        LocalDateTime newEndTime = newStartTime.plusMinutes(30);

        when(scheduleService.isDoctorAvailableForSchedule(doctor.getId(), newStartTime, newEndTime)).thenReturn(true);

        appointmentService.rescheduleAppointment(appointment, newStartTime, newEndTime);

        assertEquals(newStartTime, appointment.getStartTime());
        assertEquals(newEndTime, appointment.getEndTime());
        verify(appointmentRepository, times(1)).addAppointment(appointment);
    }

    @Test
    void testRescheduleAppointmentDoctorNotAvailable() {
        Appointment appointment = new Appointment(doctor, patient, startTime, endTime);
        LocalDateTime newStartTime = startTime.plusDays(1);
        LocalDateTime newEndTime = newStartTime.plusMinutes(30);

        when(scheduleService.isDoctorAvailableForSchedule(doctor.getId(), newStartTime, newEndTime)).thenReturn(false);

        AppointmentException exception = assertThrows(AppointmentException.class, () -> appointmentService.rescheduleAppointment(appointment, newStartTime, newEndTime));

        assertEquals("Doctor is not available at this time.", exception.getMessage());
        verify(appointmentRepository, never()).addAppointment(appointment);
    }

    @Test
    void testCreateAppointmentOverlap() {
        when(scheduleService.isDoctorAvailableForSchedule(doctor.getId(), startTime, endTime)).thenReturn(true);

        Appointment existingAppointment = new Appointment(doctor, patient, startTime.minusMinutes(15), endTime.plusMinutes(15));
        when(appointmentRepository.findByDoctorId(doctor.getId())).thenReturn(List.of(existingAppointment));

        AppointmentException exception = assertThrows(AppointmentException.class, () ->
                appointmentService.createAppointment(patient, doctor, startTime, endTime)
        );

        assertEquals(AppointmentException.APPOINTMENT_OVERLAP, exception.getMessage());
        verify(appointmentRepository, never()).addAppointment(any(Appointment.class));
    }

    @Test
    void testCreateAppointmentWithDefaultDurationSuccess() throws AppointmentException {
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusMinutes(15);

        Schedule mockSchedule = mock(Schedule.class);
        WorkingHours workingHours = new WorkingHours(startTime.toLocalDate(), startTime.toLocalTime().minusMinutes(30), endTime.toLocalTime().plusMinutes(30));
        when(mockSchedule.getWorkingHours()).thenReturn(Map.of(startTime.toLocalDate(), List.of(workingHours)));
        when(scheduleService.findScheduleByDoctorId(doctor.getId())).thenReturn(Optional.of(mockSchedule));

        when(scheduleService.isDoctorAvailableForSchedule(doctor.getId(), startTime, endTime)).thenReturn(true);
        when(appointmentRepository.findByDoctorId(doctor.getId())).thenReturn(Collections.emptyList());

        appointmentService.createAppointment(patient, doctor, startTime);

        verify(appointmentRepository, times(1)).addAppointment(any(Appointment.class));
    }

    @Test
    void testCreateAppointmentWithDefaultDurationDoctorNotAvailable() {
        when(scheduleService.isDoctorAvailableForSchedule(doctor.getId(), startTime, startTime.plusMinutes(15))).thenReturn(false);

        AppointmentException exception = assertThrows(AppointmentException.class, () -> appointmentService.createAppointment(patient, doctor, startTime));

        assertEquals(AppointmentException.DOCTOR_NOT_AVAILABLE, exception.getMessage());
        verify(appointmentRepository, never()).addAppointment(any(Appointment.class));
    }

    @Test
    void testCreateAppointmentWithDefaultDurationOverlap() {
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusMinutes(15);

        // Mock the doctor's schedule
        Schedule mockSchedule = mock(Schedule.class);
        WorkingHours workingHours = new WorkingHours(startTime.toLocalDate(), startTime.toLocalTime().minusMinutes(30), endTime.toLocalTime().plusMinutes(30));
        when(mockSchedule.getWorkingHours()).thenReturn(Map.of(startTime.toLocalDate(), List.of(workingHours)));
        when(scheduleService.findScheduleByDoctorId(doctor.getId())).thenReturn(Optional.of(mockSchedule));

        // Ensure the doctor is available for the schedule
        when(scheduleService.isDoctorAvailableForSchedule(doctor.getId(), startTime, endTime)).thenReturn(true);

        // Mock existing appointments
        Appointment existingAppointment = new Appointment(doctor, patient, startTime.minusMinutes(5), startTime.plusMinutes(20));
        when(appointmentRepository.findByDoctorId(doctor.getId())).thenReturn(List.of(existingAppointment));

        // Act and Assert
        AppointmentException exception = assertThrows(AppointmentException.class, () -> appointmentService.createAppointment(patient, doctor, startTime));
        assertEquals(AppointmentException.APPOINTMENT_OVERLAP, exception.getMessage());
        verify(appointmentRepository, never()).addAppointment(any(Appointment.class));
    }

    @Test
    void testDoctorAvailableWithinWorkingHours() {
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusMinutes(15);

        // Mock the doctor's schedule
        Schedule mockSchedule = mock(Schedule.class);
        WorkingHours workingHours = new WorkingHours(startTime.toLocalDate(), startTime.toLocalTime().minusMinutes(30), endTime.toLocalTime().plusMinutes(30));
        when(mockSchedule.getWorkingHours()).thenReturn(Map.of(startTime.toLocalDate(), List.of(workingHours)));
        when(scheduleService.findScheduleByDoctorId(doctor.getId())).thenReturn(Optional.of(mockSchedule));

        // Act
        boolean isAvailable = appointmentService.isDoctorAvailableForAppointment(doctor.getId(), startTime, endTime);

        // Assert
        assertTrue(isAvailable);
    }
}