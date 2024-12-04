package pl.wsb.lab.medicalclinic.doctor;

import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;
import pl.wsb.lab.medicalclinic.shared.model.Person;

import java.time.LocalDate;
import java.util.*;

public class Doctor extends Person {
    private final Set<MedicalSpecialty> specialties;
    private final UUID id;

    Doctor(String firstName, String lastName, LocalDate dateOfBirth, ContactInfo contactInfo, String pesel, Collection<MedicalSpecialty> specialties) {
        super(firstName, lastName, dateOfBirth, contactInfo, pesel);
        this.id = UUID.randomUUID();
        this.specialties = specialties != null ? new HashSet<>(specialties) : new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public Set<MedicalSpecialty> getSpecialties() {
        return specialties;
    }

    public void addSpecialty(MedicalSpecialty specialty) {
        this.specialties.add(specialty);
    }

    public void removeSpecialty(MedicalSpecialty specialty) {
        this.specialties.remove(specialty);
    }
}
