package pl.wsb.lab.medicalclinic.appointment;

import pl.wsb.lab.medicalclinic.patient.Patient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

interface AppointmentRepository {
    void addAppointment(Appointment appointment);

    void removeAppointment(Appointment appointment);

    List<Appointment> findByDoctorId(UUID doctorId);

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByTime(LocalDateTime time);
}
