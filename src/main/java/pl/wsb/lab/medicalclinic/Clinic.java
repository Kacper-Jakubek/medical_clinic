package pl.wsb.lab.medicalclinic;

import pl.wsb.lab.medicalclinic.patient.InMemoryPatientRepository;
import pl.wsb.lab.medicalclinic.patient.PatientRepository;

public class Clinic {
    private PatientRepository patientRepository;

    public Clinic() {
        this.patientRepository = new InMemoryPatientRepository();
    }


}
