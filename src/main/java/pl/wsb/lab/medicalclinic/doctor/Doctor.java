package pl.wsb.lab.medicalclinic.doctor;

import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;
import pl.wsb.lab.medicalclinic.shared.model.Person;

import java.time.LocalDate;
import java.util.*;

public class Doctor extends Person {
    private final Set<MedicalSpecialty> specialties;
    private final UUID id;

    Doctor(UUID id, String firstName, String lastName, LocalDate dateOfBirth, ContactInfo contactInfo, String pesel, Collection<MedicalSpecialty> specialties) {
        super(firstName, lastName, dateOfBirth, contactInfo, pesel);
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    private String specialtiesToString() {
        StringJoiner specialtiesString = new StringJoiner(", ");
        for (MedicalSpecialty specialty : specialties) {
            specialtiesString.add(specialty.toString());
        }
        return specialtiesString.toString();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", dateOfBirth=" + getDateOfBirth().toString() +
                ", contactInfo=" + getContactInfo().toString() +
                ", pesel='" + getPesel() + '\'' +
                ", specialties=" + specialtiesToString() +
                '}';
    }
}
