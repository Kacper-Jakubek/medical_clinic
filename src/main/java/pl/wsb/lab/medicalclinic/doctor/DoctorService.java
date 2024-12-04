package pl.wsb.lab.medicalclinic.doctor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void addDoctor(Doctor doctor) {
        doctorRepository.addDoctor(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        doctorRepository.removeDoctor(doctor);
    }

    public Optional<Doctor> findDoctorById(UUID id) {
        return doctorRepository.findDoctorById(id);
    }

    public List<Doctor> findDoctorsBySpecialty(MedicalSpecialty specialty) {
        return doctorRepository.findDoctorsBySpecialty(specialty);
    }

    public void addSpecialtyToDoctor(UUID doctorId, MedicalSpecialty specialty) {
        Optional<Doctor> doctor = doctorRepository.findDoctorById(doctorId);
        doctor.ifPresent(d -> {
            d.addSpecialty(specialty);
            doctorRepository.addDoctor(d);
        });
    }

    public void removeSpecialtyFromDoctor(UUID doctorId, MedicalSpecialty specialty) {
        Optional<Doctor> doctor = doctorRepository.findDoctorById(doctorId);
        doctor.ifPresent(d -> {
            d.removeSpecialty(specialty);
            doctorRepository.addDoctor(d);
        });
    }
}