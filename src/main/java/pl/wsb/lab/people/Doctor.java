package pl.wsb.lab.people;

import java.time.LocalDate;

public class Doctor extends Person {
    private MedicalSpecialty specialty;

    public Doctor(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email, String pesel, MedicalSpecialty specialty) {
        super(firstName, lastName, dateOfBirth, phoneNumber, email, pesel);
        this.specialty = specialty;
    }

    public MedicalSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MedicalSpecialty specialty) {
        this.specialty = specialty;
    }
}
