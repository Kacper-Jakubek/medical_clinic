package pl.wsb.lab.medicalclinic.doctor;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class DoctorRepository {
    private final HashMap<UUID, Doctor> doctors = new HashMap<>();

    public Optional<Doctor> getDoctorById(UUID id) {
        return Optional.ofNullable(doctors.get(id));
    }

    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }
}
