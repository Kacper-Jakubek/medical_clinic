package pl.wsb.lab.medicalclinic.domain.patient.repository;

import jakarta.annotation.Nullable;
import pl.wsb.lab.medicalclinic.domain.patient.model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientRepository {
    private final HashMap<String, Patient> patients = new HashMap<>();

    public void addPatient(Patient patient) {
        patients.put(patient.getPesel(), patient);
    }

    @Nullable
    public Patient findByPesel(String pesel) {
        return patients.get(pesel);
    }

    public List<Patient> findByLastName(String lastName) {
        List<Patient> matchingPatients = new ArrayList<>();
        for (Patient patient : patients.values()) {
            if (patient.getLastName().equals(lastName)) {
                matchingPatients.add(patient);
            }
        }
        return matchingPatients;
    }
}
