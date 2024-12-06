package pl.wsb.lab.medicalclinic.appointment;

import pl.wsb.lab.medicalclinic.doctor.Doctor;
import pl.wsb.lab.medicalclinic.patient.Patient;

import java.time.LocalDateTime;

public class Appointment {
    private Doctor doctor;
    private final Patient patient;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Appointment(Doctor doctor, Patient patient, LocalDateTime startTime, LocalDateTime endTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    Appointment(Doctor doctor, Patient patient, LocalDateTime startTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(15);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean overlaps(Appointment other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    public String toString() {
        return "Appointment{" +
                "doctor=" + doctor.toString() +
                ", patient=" + patient.toString() +
                ", startTime=" + startTime.toString() +
                ", endTime=" + endTime.toString() +
                '}';
    }
}
