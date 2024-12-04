package pl.wsb.lab;

import pl.wsb.lab.medicalclinic.domain.doctor.model.Doctor;
import pl.wsb.lab.medicalclinic.domain.doctor.model.MedicalSpecialty;
import pl.wsb.lab.medicalclinic.domain.patient.model.Patient;
import pl.wsb.lab.medicalclinic.domain.patient.repository.PatientRepository;
import pl.wsb.lab.utils.DateParser;
import pl.wsb.lab.utils.SpecialtiesParser;

import java.time.LocalDate;
import java.util.ArrayList;

public class Clinic {
    private PatientRepository patientRepository;

    public Clinic() {
        this.patientRepository = new PatientRepository();
    }
    public Patient createPatientProfile(String firstName, String lastName, String dateOfBirth, String phoneNumber, String email, String pesel) {
        LocalDate dob = DateParser.parseDate(dateOfBirth);
        Patient newPatient = new Patient(firstName, lastName, dob, phoneNumber, email, pesel);
        this.patientRepository.addPatient(newPatient);
        return newPatient;
    }

    public Doctor createDoctorProfile(String firstName, String lastName, String dateOfBirth, String phoneNumber, String email, String pesel, String medicalSpecialties) {
        LocalDate dob = DateParser.parseDate(dateOfBirth);
        ArrayList<MedicalSpecialty> specialties = (ArrayList<MedicalSpecialty>) SpecialtiesParser.parseSpecialties(medicalSpecialties);
        return new Doctor(firstName, lastName, dob, phoneNumber, email, pesel, specialties);
    }
}
