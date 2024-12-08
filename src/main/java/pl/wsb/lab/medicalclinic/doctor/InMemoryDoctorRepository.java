package pl.wsb.lab.medicalclinic.doctor;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryDoctorRepository implements DoctorRepository {
    private final HashMap<UUID, Doctor> doctors = new HashMap<>();

    @Override
    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }

    @Override
    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor.getId());
    }

    @Override
    public Optional<Doctor> findDoctorById(UUID id) {
        return Optional.ofNullable(doctors.get(id));
    }

    @Override
    public List<Doctor> findDoctorsBySpecialty(MedicalSpecialty specialty) {
        return doctors.values().stream()
                .filter(doctor -> doctor.getSpecialties().contains(specialty))
                .collect(Collectors.toList());
    }
}