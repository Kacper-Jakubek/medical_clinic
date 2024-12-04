package pl.wsb.lab.medicalclinic.test.domain;

import pl.wsb.lab.medicalclinic.domain.patient.model.Patient;
import pl.wsb.lab.medicalclinic.domain.patient.repository.PatientRepository;

import java.util.List;

public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> findPatientsByLastName(String lastName) {
        return patientRepository.findByLastName(lastName);
    }
}