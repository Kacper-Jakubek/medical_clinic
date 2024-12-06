package pl.wsb.lab.medicalclinic.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WorkingHours {
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public WorkingHours(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(date, startTime);
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(date, endTime);
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Hours: " + startTime + "-" + endTime;
    }
}
