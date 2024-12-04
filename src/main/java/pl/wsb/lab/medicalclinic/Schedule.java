package pl.wsb.lab.medicalclinic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Schedule
{
    private int doctorId;
    private List<WorkingHours> workingHours;

    public Schedule(int doctorId)
    {
        this.doctorId = doctorId;
        this.workingHours = new ArrayList<>();
    }

    public int getDoctorId()
    {
        return doctorId;
    }

    public List<WorkingHours> getWorkingHours()
    {
        return workingHours;
    }

    public void addWorkingHours(WorkingHours hours)
    {
        this.workingHours.add(hours);
    }

    public List<WorkingHours> getNextWeekSchedule()
    {
        LocalDate today = LocalDate.now();
        LocalDate endOfNextWeek = today.plusDays(7);

        List<WorkingHours> nextWeekSchedule = new ArrayList<>();
        for (WorkingHours hours : workingHours)
        {
            if (!hours.getDate().isBefore(today) && !hours.getDate().isAfter(endOfNextWeek))
            {
                nextWeekSchedule.add(hours);
            }
        }
        return nextWeekSchedule;
    }
}
