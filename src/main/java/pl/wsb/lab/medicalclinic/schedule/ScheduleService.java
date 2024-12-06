package pl.wsb.lab.medicalclinic.schedule;

import pl.wsb.lab.medicalclinic.doctor.Doctor;
import pl.wsb.lab.medicalclinic.doctor.DoctorRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    public boolean isDoctorAvailable(UUID doctorId, LocalDateTime timeFrom, LocalDateTime timeTo) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findByDoctorId(doctorId);
        if (scheduleOptional.isPresent()) {
            Schedule schedule = scheduleOptional.get();
            for (Map.Entry<LocalDate, List<WorkingHours>> entry : schedule.getWorkingHours().entrySet()) {
                if (!entry.getKey().isEqual(timeFrom.toLocalDate())) {
                    continue;
                }
                for (WorkingHours workingHours : entry.getValue()) {
                    if (workingHours.getStartTime().isBefore(timeFrom.toLocalTime()) && workingHours.getEndTime().isAfter(timeTo.toLocalTime())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}