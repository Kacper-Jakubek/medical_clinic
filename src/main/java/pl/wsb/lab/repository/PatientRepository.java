package pl.wsb.lab.repository;

import pl.wsb.lab.people.Patient;

import java.util.HashMap;
import java.util.LinkedList;

public class PatientRepository {
    private LinkedList<Patient> patients = new LinkedList<>();

    public PatientRepository(LinkedList<Patient> patients) {
        this.patients = patients;
    }

    public Patient getPatientByPesel(String pesel) {
        for (Patient patient : patients) {
            if (patient.getPesel().equals(pesel)) {
                return patient;
            }
        }
        return null;
    }

    public Patient getPatientBy
}
