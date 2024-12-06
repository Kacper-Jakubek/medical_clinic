package pl.wsb.lab.medicalclinic.patient;

import java.util.List;
import java.util.Optional;


public interface PatientRepository {

    void addPatient(Patient patient);

    Optional<Patient> findByPesel(String pesel);

    List<Patient> findByLastName(String lastName);

    void removePatient(Patient patient);
}
