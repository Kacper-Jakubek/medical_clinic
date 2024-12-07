package pl.wsb.lab.medicalclinic.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.doctor.Doctor;
import pl.wsb.lab.medicalclinic.doctor.DoctorFactory;
import pl.wsb.lab.medicalclinic.doctor.DoctorRepository;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    private DoctorRepository doctorRepository;
    private ScheduleRepository scheduleRepository;
    private ScheduleService scheduleService;
    private UUID doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ContactInfo contactInfo;
    private String pesel;

    @BeforeEach
    void setUp() {
        doctorRepository = mock(DoctorRepository.class);
        scheduleRepository = mock(ScheduleRepository.class);
        scheduleService = new ScheduleService(doctorRepository, scheduleRepository);
        doctorId = UUID.randomUUID();
        date = LocalDate.now();
        startTime = LocalTime.of(9, 0);
        endTime = LocalTime.of(17, 0);
        contactInfo = new ContactInfo("123456789", "test@example.com");
        pesel = "12345678901";
    }

    @Test
    void testCreateSchedule() {
        Doctor doctor = DoctorFactory.createDoctor(doctorId, "John", "Doe", LocalDate.of(1980, 1, 1), contactInfo, pesel, Collections.emptyList());
        when(doctorRepository.findDoctorById(doctorId)).thenReturn(Optional.of(doctor));
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(Optional.empty());

        scheduleService.createSchedule(doctorId, date, startTime, endTime);

        verify(scheduleRepository, times(1)).addSchedule(any(Schedule.class));
    }

    @Test
    void testCreateScheduleDoctorNotFound() {
        when(doctorRepository.findDoctorById(doctorId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                scheduleService.createSchedule(doctorId, date, startTime, endTime)
        );

        assertEquals("Doctor with ID: '" + doctorId.toString() + "' not found.", exception.getMessage());
    }

    @Test
    void testCreateScheduleWithExistingSchedule() {
        Doctor doctor = DoctorFactory.createDoctor(doctorId, "John", "Doe", LocalDate.of(1980, 1, 1), contactInfo, pesel, Collections.emptyList());
        Schedule existingSchedule = new Schedule(doctorId, new HashMap<>());
        when(doctorRepository.findDoctorById(doctorId)).thenReturn(Optional.of(doctor));
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(existingSchedule));

        scheduleService.createSchedule(doctorId, date, startTime, endTime);

        verify(scheduleRepository, never()).addSchedule(existingSchedule);
        assertEquals(1, existingSchedule.getWorkingHours().size());
        assertTrue(existingSchedule.getWorkingHours().containsKey(date));
        assertEquals(1, existingSchedule.getWorkingHours().get(date).size());
        assertEquals(startTime, existingSchedule.getWorkingHours().get(date).getFirst().getStartTime());
        assertEquals(endTime, existingSchedule.getWorkingHours().get(date).getFirst().getEndTime());
    }

    @Test
    void testCreateScheduleWithNullDoctorId() {
        assertThrows(NullPointerException.class, () ->
                scheduleService.createSchedule(null, date, startTime, endTime)
        );
    }

    @Test
    void testCreateScheduleWithNullDate() {
        Doctor doctor = DoctorFactory.createDoctor(doctorId, "John", "Doe", LocalDate.of(1980, 1, 1), contactInfo, pesel, Collections.emptyList());
        when(doctorRepository.findDoctorById(doctorId)).thenReturn(Optional.of(doctor));

        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.createSchedule(doctorId, null, startTime, endTime)
        );
    }

    @Test
    void testCreateScheduleWithNullStartTime() {
        Doctor doctor = DoctorFactory.createDoctor(doctorId, "John", "Doe", LocalDate.of(1980, 1, 1), contactInfo, pesel, Collections.emptyList());
        when(doctorRepository.findDoctorById(doctorId)).thenReturn(Optional.of(doctor));

        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.createSchedule(doctorId, date, null, endTime)
        );
    }

    @Test
    void testCreateScheduleWithNullEndTime() {
        Doctor doctor = DoctorFactory.createDoctor(doctorId, "John", "Doe", LocalDate.of(1980, 1, 1), contactInfo, pesel, Collections.emptyList());
        when(doctorRepository.findDoctorById(doctorId)).thenReturn(Optional.of(doctor));

        assertThrows(IllegalArgumentException.class, () ->
                scheduleService.createSchedule(doctorId, date, startTime, null)
        );
    }
}