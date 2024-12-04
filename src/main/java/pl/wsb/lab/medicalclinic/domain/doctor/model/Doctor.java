package pl.wsb.lab.medicalclinic.domain.doctor.model;

import pl.wsb.lab.medicalclinic.domain.shared.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Doctor extends Person {
    private final ArrayList<MedicalSpecialty> specialties;
    private final UUID id;

    public Doctor(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email, String pesel, ArrayList<MedicalSpecialty> specialties) {
        super(firstName, lastName, dateOfBirth, phoneNumber, email, pesel);
        this.id = UUID.randomUUID();
        this.specialties = specialties;
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<MedicalSpecialty> getSpecialties() {
        return specialties;
    }

    public void addSpecialty(MedicalSpecialty specialty) {
        this.specialties.add(specialty);
    }

    public void removeSpecialty(MedicalSpecialty specialty) {
        this.specialties.remove(specialty);
    }
}
