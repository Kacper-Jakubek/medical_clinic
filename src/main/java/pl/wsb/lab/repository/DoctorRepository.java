package pl.wsb.lab.repository;

import pl.wsb.lab.people.Doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DoctorRepository {
    private final HashMap<UUID, Doctor> doctors = new HashMap<>();

    public DoctorRepository() {
    }

    public Doctor getDoctorById(UUID id) {
        return doctors.get(id);
    }

    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }
}
