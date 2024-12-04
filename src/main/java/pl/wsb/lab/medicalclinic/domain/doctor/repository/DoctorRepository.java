package pl.wsb.lab.medicalclinic.domain.doctor.repository;

import pl.wsb.lab.medicalclinic.domain.doctor.model.Doctor;

import java.util.HashMap;
import java.util.UUID;

public class DoctorRepository {
    private final HashMap<UUID, Doctor> doctors = new HashMap<>();

    public Doctor getDoctorById(UUID id) {
        return doctors.get(id);
    }

    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }
}
