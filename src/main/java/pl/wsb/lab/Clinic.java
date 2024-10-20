package pl.wsb.lab;

import java.util.List;

public class Clinic {

    private List<Patient> patients;

    public Patient createPatientProfile(String firstName, String lastName) {
        Patient newPatient = new Patient(firstName, lastName);
        this.patients.add(newPatient);
        return newPatient;
    }

    public Patient getPatientByPesel(String pesel) {
        // your implementation
        return null;
    }
}
