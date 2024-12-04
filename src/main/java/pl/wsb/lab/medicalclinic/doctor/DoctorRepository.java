package pl.wsb.lab.medicalclinic.doctor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository {
    void addDoctor(Doctor doctor);
    void removeDoctor(Doctor doctor);
    Optional<Doctor> findDoctorById(UUID id);
    List<Doctor> findDoctorsBySpecialty(MedicalSpecialty specialty);
}
