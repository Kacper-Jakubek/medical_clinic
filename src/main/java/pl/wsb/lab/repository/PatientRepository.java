package pl.wsb.lab.repository;

import pl.wsb.lab.people.Patient;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientRepository {
    private final HashMap<String, Patient> patients = new HashMap<>();

    public PatientRepository() {
    }

    public void addPatient(Patient patient) {
        patients.put(patient.getPesel(), patient);
    }

    public Patient getPatientByPesel(String pesel) {
        return patients.get(pesel);
    }

    public Patient getPatientByLastName(String lastName) {
        for (Patient patient : patients) {
            if (patient.getLastName().equals(lastName)) {
                return patient;
            }
        }
        return null;
    }
}
