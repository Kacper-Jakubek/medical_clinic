package pl.wsb.lab.medicalclinic.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.doctor.DoctorRepository;
import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduleFactoryTest {
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
    void testIsDoctorAvailableForScheduleWhenAvailable() {
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(Optional.empty());

        LocalDateTime timeFrom = LocalDateTime.of(date, startTime);
        LocalDateTime timeTo = LocalDateTime.of(date, endTime);

        assertTrue(scheduleService.isDoctorAvailableForSchedule(doctorId, timeFrom, timeTo));
    }

    @Test
    void testIsDoctorAvailableForScheduleWhenNotAvailable() {
        WorkingHours workingHours = new WorkingHours(date, LocalTime.of(8, 0), LocalTime.of(10, 0));
        Schedule schedule = new Schedule(doctorId, Map.of(date, List.of(workingHours)));
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(schedule));

        LocalDateTime timeFrom = LocalDateTime.of(date, LocalTime.of(9, 0));
        LocalDateTime timeTo = LocalDateTime.of(date, LocalTime.of(11, 0));

        assertFalse(scheduleService.isDoctorAvailableForSchedule(doctorId, timeFrom, timeTo));
    }

    @Test
    void testIsDoctorAvailableForScheduleWhenOverlapping() {
        WorkingHours workingHours = new WorkingHours(date, LocalTime.of(10, 0), LocalTime.of(12, 0));
        Schedule schedule = new Schedule(doctorId, Map.of(date, List.of(workingHours)));
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(schedule));

        LocalDateTime timeFrom = LocalDateTime.of(date, LocalTime.of(11, 0));
        LocalDateTime timeTo = LocalDateTime.of(date, LocalTime.of(13, 0));

        assertFalse(scheduleService.isDoctorAvailableForSchedule(doctorId, timeFrom, timeTo));
    }

    @Test
    void testIsDoctorAvailableForScheduleWhenExactMatch() {
        WorkingHours workingHours = new WorkingHours(date, startTime, endTime);
        Schedule schedule = new Schedule(doctorId, Map.of(date, List.of(workingHours)));
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(schedule));

        LocalDateTime timeFrom = LocalDateTime.of(date, startTime);
        LocalDateTime timeTo = LocalDateTime.of(date, endTime);

        assertFalse(scheduleService.isDoctorAvailableForSchedule(doctorId, timeFrom, timeTo));
    }

    @Test
    void testCreateScheduleWithMap() {
        UUID doctorId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        WorkingHours workingHours = new WorkingHours(date, startTime, endTime);
        Map<LocalDate, List<WorkingHours>> workingHoursMap = new HashMap<>();
        workingHoursMap.put(date, Collections.singletonList(workingHours));

        Schedule schedule = ScheduleFactory.createSchedule(doctorId, workingHoursMap);

        assertNotNull(schedule);
        assertEquals(doctorId, schedule.getDoctorId());
        assertEquals(1, schedule.getWorkingHours().size());
        assertTrue(schedule.getWorkingHours().containsKey(date));
        assertEquals(1, schedule.getWorkingHours().get(date).size());
        assertEquals(workingHours, schedule.getWorkingHours().get(date).get(0));
    }

    @Test
    void testCreateScheduleWithWorkingHours() {
        UUID doctorId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        WorkingHours workingHours = new WorkingHours(date, startTime, endTime);

        Schedule schedule = ScheduleFactory.createSchedule(doctorId, workingHours);

        assertNotNull(schedule);
        assertEquals(doctorId, schedule.getDoctorId());
        assertEquals(1, schedule.getWorkingHours().size());
        assertTrue(schedule.getWorkingHours().containsKey(date));
        assertEquals(1, schedule.getWorkingHours().get(date).size());
        assertEquals(workingHours, schedule.getWorkingHours().get(date).get(0));
    }

    @Test
    void testCreateScheduleWithStrings() {
        UUID doctorId = UUID.randomUUID();
        String date = LocalDate.now().toString();
        String startTime = "09:00";
        String endTime = "17:00";

        Schedule schedule = ScheduleFactory.createSchedule(doctorId, date, startTime, endTime);

        assertNotNull(schedule);
        assertEquals(doctorId, schedule.getDoctorId());
        LocalDate parsedDate = LocalDate.parse(date);
        assertEquals(1, schedule.getWorkingHours().size());
        assertTrue(schedule.getWorkingHours().containsKey(parsedDate));
        assertEquals(1, schedule.getWorkingHours().get(parsedDate).size());
        WorkingHours workingHours = schedule.getWorkingHours().get(parsedDate).get(0);
        assertEquals(LocalTime.parse(startTime), workingHours.getStartTime());
        assertEquals(LocalTime.parse(endTime), workingHours.getEndTime());
    }

    @Test
    void testCreateScheduleWithInvalidTime() {
        UUID doctorId = UUID.randomUUID();
        String date = LocalDate.now().toString();
        String startTime = "17:00";
        String endTime = "09:00";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ScheduleFactory.createSchedule(doctorId, date, startTime, endTime)
        );

        assertEquals("Start time cannot be after end time", exception.getMessage());
    }

    @Test
    void testCreateScheduleWithNullTime() {
        UUID doctorId = UUID.randomUUID();
        String date = LocalDate.now().toString();
        String startTime = null;
        String endTime = "17:00";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ScheduleFactory.createSchedule(doctorId, date, startTime, endTime)
        );

        assertEquals("Start and end time cannot be null", exception.getMessage());
    }

    @Test
    void testGetWorkingHoursWithNullDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ScheduleFactory.getWorkingHours(null, "09:00", "17:00")
        );
        assertEquals("Start and end time cannot be null", exception.getMessage());
    }

    @Test
    void testGetWorkingHoursWithNullStartTime() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ScheduleFactory.getWorkingHours("2023-10-10", null, "17:00")
        );
        assertEquals("Start and end time cannot be null", exception.getMessage());
    }

    @Test
    void testGetWorkingHoursWithNullEndTime() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ScheduleFactory.getWorkingHours("2023-10-10", "09:00", null)
        );
        assertEquals("Start and end time cannot be null", exception.getMessage());
    }

    @Test
    void testGetWorkingHoursWithInvalidDateFormat() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ScheduleFactory.getWorkingHours("invalid-date", "09:00", "17:00")
        );
        assertEquals("Invalid date format. Please use one of the following formats: yyyy-MM-dd, yyyy.MM.dd, dd.MM.yyyy, dd-MM-yyyy", exception.getMessage());
    }

    @Test
    void testGetWorkingHoursWithInvalidStartTimeFormat() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ScheduleFactory.getWorkingHours("2023-10-10", "invalid-time", "17:00")
        );
        assertEquals("Invalid start time format. Please use one of the following formats: HH:mm, HH.mm", exception.getMessage());
    }

    @Test
    void testGetWorkingHoursWithInvalidEndTimeFormat() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ScheduleFactory.getWorkingHours("2023-10-10", "09:00", "invalid-time")
        );
        assertEquals("Invalid end time format. Please use one of the following formats: HH:mm, HH.mm", exception.getMessage());
    }

    @Test
    void testIsDoctorAvailableForScheduleWhenExactStartTimeMatch() {
        WorkingHours workingHours = new WorkingHours(date, startTime, LocalTime.of(12, 0));
        Schedule schedule = new Schedule(doctorId, Map.of(date, List.of(workingHours)));
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(schedule));

        LocalDateTime timeFrom = LocalDateTime.of(date, startTime);
        LocalDateTime timeTo = LocalDateTime.of(date, LocalTime.of(10, 0));

        assertFalse(scheduleService.isDoctorAvailableForSchedule(doctorId, timeFrom, timeTo));
    }

    @Test
    void testIsDoctorAvailableForScheduleWhenExactEndTimeMatch() {
        WorkingHours workingHours = new WorkingHours(date, LocalTime.of(8, 0), endTime);
        Schedule schedule = new Schedule(doctorId, Map.of(date, List.of(workingHours)));
        when(scheduleRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(schedule));

        LocalDateTime timeFrom = LocalDateTime.of(date, LocalTime.of(16, 0));
        LocalDateTime timeTo = LocalDateTime.of(date, endTime);

        assertFalse(scheduleService.isDoctorAvailableForSchedule(doctorId, timeFrom, timeTo));
    }
}