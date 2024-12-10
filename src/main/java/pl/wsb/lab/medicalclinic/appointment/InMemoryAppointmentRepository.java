package pl.wsb.lab.medicalclinic.appointment;

import pl.wsb.lab.medicalclinic.patient.Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryAppointmentRepository implements AppointmentRepository {
    private final List<Appointment> appointments = new ArrayList<>();

    @Override
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    @Override
    public List<Appointment> findByTime(LocalDateTime time) {
        return appointments.stream()
                .filter(appointment -> !appointment.getStartTime().isAfter(time)
                        && !appointment.getEndTime().isBefore(time))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByPatient(Patient patient) {
        return appointments.stream()
                .filter(appointment -> appointment.getPatient().equals(patient))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    @Override
    public List<Appointment> findByDoctorId(UUID doctorId) {
        return appointments.stream()
                .filter(appointment -> appointment.getDoctor().getId().equals(doctorId))
                .collect(Collectors.toList());
    }
}
