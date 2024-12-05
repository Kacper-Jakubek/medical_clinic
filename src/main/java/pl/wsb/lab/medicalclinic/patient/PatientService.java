package pl.wsb.lab.medicalclinic.patient;

import java.util.List;
import java.util.Optional;

public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void addPatient(Patient patient) {
        patientRepository.addPatient(patient);
    }

    public void removePatient(Patient patient) {
        patientRepository.removePatient(patient);
    }

    public List<Patient> searchByLastName(String lastName) {
        return this.patientRepository.findByLastName(lastName);
    }

    public Optional<Patient> searchByPesel(String pesel) {
        return this.patientRepository.findByPesel(pesel);
    }
}
