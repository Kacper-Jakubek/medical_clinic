package pl.wsb.lab;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Schedule
{
    private int doctorId;
    private Map<DayOfWeek, WorkingHours> schedule;

    public Schedule(int doctorId)
    {
        this.doctorId = doctorId;
        this.schedule = new HashMap<>();
    }

    public int getDoctorId()
    {
        return doctorId;
    }

    public void setDoctorId(int doctorId)
    {
        this.doctorId = doctorId;
    }

    //add working hours for a specific day
    public void addWorkingHours(DayOfWeek day, LocalTime start, LocalTime end)
    {
        schedule.put(day, new WorkingHours(start, end));
    }

    public WorkingHours getWorkingHours(DayOfWeek day)
    {
        return schedule.get(day);
    }

    public Map<DayOfWeek, WorkingHours> getSchedule()
    {
        return schedule;
    }

    public static class WorkingHours
    {
        private LocalTime start;
        private LocalTime end;

        public WorkingHours(LocalTime start, LocalTime end)
        {
            this.start = start;
            this.end = end;
        }

        public LocalTime getStart()
        {
            return start;
        }

        public LocalTime getEnd()
        {
            return end;
        }

        @Override
        public String toString()
        {
            return "from " + start + " to " + end;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Doctor ID: ").append(doctorId).append("\nSchedule:\n");
        for (DayOfWeek day : schedule.keySet())
        {
            sb.append(day).append(": ").append(schedule.get(day)).append("\n");
        }
        return sb.toString();
    }
}
