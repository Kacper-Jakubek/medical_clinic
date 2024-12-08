package pl.wsb.lab.medicalclinic.schedule;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleFactoryTest {

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
}