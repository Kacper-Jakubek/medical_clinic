package pl.wsb.lab.medicalclinic.schedule;

import pl.wsb.lab.medicalclinic.shared.util.DateTimeParser;
import pl.wsb.lab.medicalclinic.shared.util.WorkingHoursValidator;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.*;

public class ScheduleFactory {
    // Private constructor to prevent instantiation
    private ScheduleFactory() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Schedule createSchedule(UUID doctorId, Map<LocalDate, List<WorkingHours>> workingHours) {
        WorkingHoursValidator.validateWorkingHours(workingHours);
        return new Schedule(doctorId, workingHours);
    }

    public static Schedule createSchedule(UUID doctorId, WorkingHours workingHours) {
        WorkingHoursValidator.validateWorkingHours(workingHours);
        Schedule schedule = new Schedule(doctorId, new HashMap<>());
        schedule.addWorkingHours(workingHours);
        return schedule;
    }

    public static Schedule createSchedule(UUID doctorId, String date, String startTime, String endTime) {
        WorkingHours workingHours = getWorkingHours(date, startTime, endTime);
        WorkingHoursValidator.validateWorkingHours(workingHours);
        return createSchedule(doctorId, workingHours);
    }

    private static WorkingHours getWorkingHours(String date, String startTime, String endTime) {
        if (date == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end time cannot be null");
        }

        LocalDate parsedDate;
        try {
            parsedDate = DateTimeParser.parseDate(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use one of the following formats: yyyy-MM-dd, yyyy.MM.dd, dd.MM.yyyy, dd-MM-yyyy");
        }
        LocalTime start;
        try {
            start = DateTimeParser.parseTime(startTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid start time format. Please use one of the following formats: HH:mm, HH.mm");
        }
        LocalTime end;
        try {
            end = DateTimeParser.parseTime(endTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid end time format. Please use one of the following formats: HH:mm, HH.mm");
        }
        return new WorkingHours(parsedDate, start, end);
    }
}