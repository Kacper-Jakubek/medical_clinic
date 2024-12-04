package pl.wsb.lab.medicalclinic.domain.patient.service;

import jakarta.annotation.Nullable;
import pl.wsb.lab.medicalclinic.domain.patient.model.Patient;
import pl.wsb.lab.medicalclinic.domain.patient.repository.PatientRepository;

import java.util.List;

public class PatientSearchService {
    private final PatientRepository patientRepository;

    public PatientSearchService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> searchByLastName(String lastName) {
        return this.patientRepository.findByLastName(lastName);
    }

    @Nullable
    public Patient searchByPesel(String pesel) {
        return this.patientRepository.findByPesel(pesel);
    }
}
