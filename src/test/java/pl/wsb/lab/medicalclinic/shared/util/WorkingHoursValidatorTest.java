package pl.wsb.lab.medicalclinic.shared.util;

import org.junit.jupiter.api.Test;
import pl.wsb.lab.medicalclinic.schedule.WorkingHours;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorkingHoursValidatorTest {

    @Test
    void testPrivateConstructor() throws NoSuchMethodException {
        Constructor<WorkingHoursValidator> constructor = WorkingHoursValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    void testValidateWorkingHoursWithValidTimes() {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        assertDoesNotThrow(() -> WorkingHoursValidator.validateWorkingHours(startTime, endTime));
    }

    @Test
    void testValidateWorkingHoursWithNullTimes() {
        assertThrows(IllegalArgumentException.class, () -> WorkingHoursValidator.validateWorkingHours(null, LocalTime.of(17, 0)));
        assertThrows(IllegalArgumentException.class, () -> WorkingHoursValidator.validateWorkingHours(LocalTime.of(9, 0), null));
    }

    @Test
    void testValidateWorkingHoursWithStartTimeAfterEndTime() {
        LocalTime startTime = LocalTime.of(18, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        assertThrows(IllegalArgumentException.class, () -> WorkingHoursValidator.validateWorkingHours(startTime, endTime));
    }

    @Test
    void testValidateWorkingHoursWithValidWorkingHours() {
        WorkingHours workingHours = new WorkingHours(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(17, 0));
        assertDoesNotThrow(() -> WorkingHoursValidator.validateWorkingHours(workingHours));
    }

    @Test
    void testValidateWorkingHoursWithInvalidWorkingHours() {
        WorkingHours workingHours = new WorkingHours(LocalDate.now(), LocalTime.of(18, 0), LocalTime.of(17, 0));
        assertThrows(IllegalArgumentException.class, () -> WorkingHoursValidator.validateWorkingHours(workingHours));
    }

    @Test
    void testValidateWorkingHoursWithValidMap() {
        LocalDate date = LocalDate.now();
        WorkingHours workingHours = new WorkingHours(date, LocalTime.of(9, 0), LocalTime.of(17, 0));
        Map<LocalDate, List<WorkingHours>> workingHoursMap = new HashMap<>();
        workingHoursMap.put(date, Collections.singletonList(workingHours));
        assertDoesNotThrow(() -> WorkingHoursValidator.validateWorkingHours(workingHoursMap));
    }

    @Test
    void testValidateWorkingHoursWithInvalidMap() {
        LocalDate date = LocalDate.now();
        WorkingHours workingHours = new WorkingHours(date.plusDays(1), LocalTime.of(9, 0), LocalTime.of(17, 0));
        Map<LocalDate, List<WorkingHours>> workingHoursMap = new HashMap<>();
        workingHoursMap.put(date, Collections.singletonList(workingHours));
        assertThrows(IllegalArgumentException.class, () -> WorkingHoursValidator.validateWorkingHours(workingHoursMap));
    }
}