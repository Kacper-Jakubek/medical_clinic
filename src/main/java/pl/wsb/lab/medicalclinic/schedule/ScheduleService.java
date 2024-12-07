package pl.wsb.lab.medicalclinic.schedule;

import pl.wsb.lab.medicalclinic.doctor.Doctor;
import pl.wsb.lab.medicalclinic.doctor.DoctorRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            LocalDateTime timeFrom = LocalDateTime.of(date, startTime);
            LocalDateTime timeTo = LocalDateTime.of(date, endTime);
            if (!isDoctorAvailableForSchedule(doctorId, timeFrom, timeTo)) {
                throw new IllegalArgumentException("Doctor is not available during the specified time.");
            }
            WorkingHours workingHours = new WorkingHours(date, startTime, endTime);
            Optional<Schedule> schedule = scheduleRepository.findByDoctorId(doctorId);
            if (schedule.isPresent()) {
                schedule.get().getWorkingHours().computeIfAbsent(date, k -> new ArrayList<>()).add(workingHours);
            } else {
                Schedule newSchedule = ScheduleFactory.createSchedule(doctorId, workingHours);
                scheduleRepository.addSchedule(newSchedule);
            }
        } else {
            throw new IllegalArgumentException("Doctor with ID: '" + doctorId.toString() + "' not found.");
        }
    }

    public boolean isDoctorAvailableForSchedule(UUID doctorId, LocalDateTime timeFrom, LocalDateTime timeTo) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findByDoctorId(doctorId);
        if (scheduleOptional.isPresent()) {
            Schedule schedule = scheduleOptional.get();
            for (Map.Entry<LocalDate, List<WorkingHours>> entry : schedule.getWorkingHours().entrySet()) {
                if (!entry.getKey().equals(timeFrom.toLocalDate())) {
                    continue;
                }
                for (WorkingHours workingHours : entry.getValue()) {
                    if ((workingHours.getStartTime().isBefore(timeTo.toLocalTime()) && workingHours.getEndTime().isAfter(timeFrom.toLocalTime())) ||
                            (workingHours.getStartTime().equals(timeFrom.toLocalTime()) || workingHours.getEndTime().equals(timeTo.toLocalTime()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public Optional<Schedule> findScheduleByDoctorId(UUID doctorId) {
        return scheduleRepository.findByDoctorId(doctorId);
    }
}