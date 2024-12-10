package pl.wsb.lab.medicalclinic.appointment;

import pl.wsb.lab.medicalclinic.doctor.Doctor;
import pl.wsb.lab.medicalclinic.patient.Patient;
import pl.wsb.lab.medicalclinic.schedule.Schedule;
import pl.wsb.lab.medicalclinic.schedule.ScheduleService;
import pl.wsb.lab.medicalclinic.schedule.WorkingHours;
import pl.wsb.lab.medicalclinic.shared.exception.AppointmentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ScheduleService scheduleService;

    public AppointmentService(AppointmentRepository appointmentRepository, ScheduleService scheduleService) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleService = scheduleService;
    }

    public void createAppointment(Patient patient, Doctor doctor, LocalDateTime startTime, LocalDateTime endTime) throws AppointmentException {
        if (!scheduleService.isDoctorAvailableForSchedule(doctor.getId(), startTime, endTime)) {
            throw new AppointmentException(AppointmentException.DOCTOR_NOT_AVAILABLE);
        }
        for (Appointment existingAppointment : appointmentRepository.findByDoctorId(doctor.getId())) {
            if (appointmentsOverlap(existingAppointment, startTime, endTime)) {
                throw new AppointmentException(AppointmentException.APPOINTMENT_OVERLAP);
            }
        }
        Appointment appointment = new Appointment(doctor, patient, startTime, endTime);
        appointmentRepository.addAppointment(appointment);
    }

    public void createAppointment(Patient patient, Doctor doctor, LocalDateTime startTime) throws AppointmentException {
        LocalDateTime endTime = startTime.plusMinutes(15);
        if (!isDoctorAvailableForAppointment(doctor.getId(), startTime, endTime)) {
            throw new AppointmentException(AppointmentException.DOCTOR_NOT_AVAILABLE);
        }
        for (Appointment existingAppointment : appointmentRepository.findByDoctorId(doctor.getId())) {
            if (appointmentsOverlap(existingAppointment, startTime, endTime)) {
                throw new AppointmentException(AppointmentException.APPOINTMENT_OVERLAP);
            }
        }
        Appointment appointment = new Appointment(doctor, patient, startTime);
        appointmentRepository.addAppointment(appointment);
    }

    private boolean appointmentsOverlap(Appointment appointment, LocalDateTime startTime, LocalDateTime endTime) {
        return !appointment.getEndTime().isBefore(startTime) && !appointment.getStartTime().isAfter(endTime);
    }

    public void cancelAppointment(Appointment appointment) {
        appointmentRepository.removeAppointment(appointment);
    }

    protected boolean isDoctorAvailableForAppointment(UUID doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        Optional<Schedule> scheduleOptional = scheduleService.findScheduleByDoctorId(doctorId);
        if (scheduleOptional.isPresent()) {
            Schedule schedule = scheduleOptional.get();
            for (Map.Entry<LocalDate, List<WorkingHours>> entry : schedule.getWorkingHours().entrySet()) {
                if (!entry.getKey().equals(startTime.toLocalDate())) {
                    continue;
                }
                for (WorkingHours workingHours : entry.getValue()) {
                    if ((workingHours.getStartTime().isBefore(endTime.toLocalTime()) && workingHours.getEndTime().isAfter(startTime.toLocalTime())) ||
                            (workingHours.getStartTime().equals(startTime.toLocalTime()) || workingHours.getEndTime().equals(endTime.toLocalTime()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void rescheduleAppointment(Appointment appointment, LocalDateTime newStartTime, LocalDateTime newEndTime) throws AppointmentException {
        if (!scheduleService.isDoctorAvailableForSchedule(appointment.getDoctor().getId(), newStartTime, newEndTime)) {
            throw new AppointmentException(AppointmentException.DOCTOR_NOT_AVAILABLE);
        }
        appointment.setStartTime(newStartTime);
        appointment.setEndTime(newEndTime);
        appointmentRepository.addAppointment(appointment);
    }
}