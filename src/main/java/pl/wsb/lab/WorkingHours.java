package pl.wsb.lab;

import java.time.LocalDate;

public class WorkingHours
{
    private LocalDate date;
    private double startHour;
    private double endHour;

    public WorkingHours(LocalDate date, double startHour, double endHour)
    {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public double getStartHour()
    {
        return startHour;
    }

    public double getEndHour()
    {
        return endHour;
    }

    @Override
    public String toString()
    {
        return "Date: " + date + ", Hours: " + startHour + "-" + endHour;
    }
}
