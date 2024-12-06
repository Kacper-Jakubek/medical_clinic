package pl.wsb.lab.medicalclinic.schedule;

import pl.wsb.lab.medicalclinic.shared.util.WorkingHoursValidator;

import java.time.LocalDate;
import java.util.*;

public class Schedule {
    private final UUID doctorId;
    private Map<LocalDate, List<WorkingHours>> workingHours;

    Schedule(UUID doctorId, Map<LocalDate, List<WorkingHours>> workingHours) {
        this.doctorId = doctorId;
        this.workingHours = workingHours;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public Map<LocalDate, List<WorkingHours>> getWorkingHours() {
        return workingHours;
    }

    public void addWorkingHours(WorkingHours workingHours) {
        WorkingHoursValidator.validateWorkingHours(workingHours);
        this.workingHours.computeIfAbsent(workingHours.getDate(), k -> new ArrayList<>()).add(workingHours);
    }

    public List<WorkingHours> getNextWeekSchedule() {
        LocalDate today = LocalDate.now();
        LocalDate endOfNextWeek = today.plusDays(7);

        List<WorkingHours> nextWeekSchedule = new ArrayList<>();
        for (LocalDate date = today; !date.isAfter(endOfNextWeek); date = date.plusDays(1)) {
            List<WorkingHours> hoursList = workingHours.get(date);
            if (hoursList != null) {
                nextWeekSchedule.addAll(hoursList);
            }
        }
        return nextWeekSchedule;
    }
}