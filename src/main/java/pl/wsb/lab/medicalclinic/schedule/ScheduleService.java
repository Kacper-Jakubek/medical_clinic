package pl.wsb.lab.medicalclinic.schedule;

import pl.wsb.lab.medicalclinic.doctor.Doctor;
import pl.wsb.lab.medicalclinic.doctor.DoctorRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ScheduleService {
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(DoctorRepository doctorRepository, ScheduleRepository scheduleRepository) {
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public void createSchedule(UUID doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end time cannot be null.");
        }
        Optional<Doctor> doctorOptional = doctorRepository.findDoctorById(doctorId);
        if (doctorOptional.isPresent()) {
            WorkingHours workingHours = new WorkingHours(date, startTime, endTime);
            Schedule schedule = scheduleRepository.findByDoctorId(doctorId)
                    .orElse(ScheduleFactory.createSchedule(doctorId, workingHours));
            schedule.addWorkingHours(workingHours);
            scheduleRepository.addSchedule(schedule);
        } else {
            throw new IllegalArgumentException("Doctor with ID: '" + doctorId.toString() + "' not found.");
        }
    }
}