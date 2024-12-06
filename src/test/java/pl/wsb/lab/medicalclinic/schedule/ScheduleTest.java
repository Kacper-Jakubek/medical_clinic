package pl.wsb.lab.medicalclinic.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScheduleTest {
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        UUID doctorId = UUID.randomUUID();
        schedule = new Schedule(doctorId, new HashMap<>());
    }

    @Test
    void testAddWorkingHours() {
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        WorkingHours workingHours = new WorkingHours(date, startTime, endTime);

        schedule.addWorkingHours(workingHours);

        assertEquals(1, schedule.getWorkingHours().size());
        assertTrue(schedule.getWorkingHours().containsKey(date));
        assertEquals(1, schedule.getWorkingHours().get(date).size());
        assertEquals(workingHours, schedule.getWorkingHours().get(date).get(0));
    }


    @Test
    void testGetNextWeekSchedule() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeekDate = today.plusDays(3);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        WorkingHours workingHours = new WorkingHours(nextWeekDate, startTime, endTime);

        schedule.addWorkingHours(workingHours);

        List<WorkingHours> nextWeekSchedule = schedule.getNextWeekSchedule();

        assertEquals(1, nextWeekSchedule.size());
        assertEquals(workingHours, nextWeekSchedule.get(0));
    }

    @Test
    void testGetNextWeekScheduleEmpty() {
        List<WorkingHours> nextWeekSchedule = schedule.getNextWeekSchedule();

        assertTrue(nextWeekSchedule.isEmpty());
    }
}