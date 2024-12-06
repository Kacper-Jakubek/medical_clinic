package pl.wsb.lab.medicalclinic.shared.util;

import pl.wsb.lab.medicalclinic.schedule.WorkingHours;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class WorkingHoursValidator {

    private WorkingHoursValidator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void validateWorkingHours(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end time cannot be null");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }

    public static void validateWorkingHours(WorkingHours workingHours) {
        validateWorkingHours(workingHours.getStartTime(), workingHours.getEndTime());
    }


    public static void validateWorkingHours(Map<LocalDate, List<WorkingHours>> workingHours) {
        for (Map.Entry<LocalDate, List<WorkingHours>> workingHoursEntry : workingHours.entrySet()) {
            LocalDate key = workingHoursEntry.getKey();
            List<WorkingHours> value = workingHoursEntry.getValue();
            for (WorkingHours hours : value) {
                validateWorkingHours(hours.getStartTime(), hours.getEndTime());
                if (!key.equals(hours.getDate())) {
                    throw new IllegalArgumentException("Date in WorkingHours object does not match the key in the map");
                }
            }
        }
    }
}
