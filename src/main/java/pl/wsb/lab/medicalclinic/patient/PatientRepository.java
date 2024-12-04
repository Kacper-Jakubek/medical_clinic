package pl.wsb.lab.medicalclinic.patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class PatientRepository {
    private final HashMap<String, Patient> patients = new HashMap<>();

    public void addPatient(Patient patient) {
        patients.put(patient.getPesel(), patient);
    }

    public Optional<Patient> findByPesel(String pesel) {
        return Optional.ofNullable(patients.get(pesel));
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
