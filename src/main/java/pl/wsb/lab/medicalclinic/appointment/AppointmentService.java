package pl.wsb.lab.medicalclinic.appointment;

import pl.wsb.lab.medicalclinic.doctor.Doctor;
import pl.wsb.lab.medicalclinic.patient.Patient;
import pl.wsb.lab.medicalclinic.schedule.ScheduleService;
import pl.wsb.lab.medicalclinic.shared.exception.AppointmentException;

import java.time.LocalDateTime;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ScheduleService scheduleService;

    public AppointmentService(AppointmentRepository appointmentRepository, ScheduleService scheduleService) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleService = scheduleService;
    }

    public void createAppointment(Patient patient, Doctor doctor, LocalDateTime startTime, LocalDateTime endTime) throws AppointmentException {
        if (!scheduleService.isDoctorAvailable(doctor.getId(), startTime, endTime)) {
            throw new AppointmentException(AppointmentException.DOCTOR_NOT_AVAILABLE);
        }
        Appointment appointment = new Appointment(doctor, patient, startTime, endTime);
        appointmentRepository.addAppointment(appointment);
    }

    private boolean appointmentsOverlap(Appointment appointment, LocalDateTime startTime, LocalDateTime endTime) {
        return !appointment.getEndTime().isBefore(startTime) && !appointment.getStartTime().isAfter(endTime);
    }

    public void cancelAppointment(Appointment appointment) {
        appointmentRepository.removeAppointment(appointment);
    }

    public void rescheduleAppointment(Appointment appointment, LocalDateTime newStartTime) throws AppointmentException {
        LocalDateTime newEndTime = newStartTime.plusMinutes(15);
        if (!scheduleService.isDoctorAvailable(appointment.getDoctor().getId(), newStartTime, newEndTime)) {
            throw new AppointmentException(AppointmentException.DOCTOR_NOT_AVAILABLE);
        }
        appointment.setStartTime(newStartTime);
        appointment.setEndTime(newEndTime);
        appointmentRepository.addAppointment(appointment);
    }

    public void rescheduleAppointment(Appointment appointment, LocalDateTime newStartTime, LocalDateTime newEndTime) throws AppointmentException {
        if (!scheduleService.isDoctorAvailable(appointment.getDoctor().getId(), newStartTime, newEndTime)) {
            throw new AppointmentException(AppointmentException.DOCTOR_NOT_AVAILABLE);
        }
        appointment.setStartTime(newStartTime);
        appointment.setEndTime(newEndTime);
        appointmentRepository.addAppointment(appointment);
    }
}