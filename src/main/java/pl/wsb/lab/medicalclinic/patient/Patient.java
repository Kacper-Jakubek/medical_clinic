package pl.wsb.lab.medicalclinic.patient;

import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;
import pl.wsb.lab.medicalclinic.shared.model.Person;

import java.time.LocalDate;

public class Patient extends Person {
    Patient(String firstName, String lastName, LocalDate dateOfBirth, ContactInfo contactInfo, String pesel) {
        super(firstName, lastName, dateOfBirth, contactInfo, pesel);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", dateOfBirth=" + getDateOfBirth().toString() +
                ", contactInfo=" + getContactInfo().toString() +
                ", pesel='" + getPesel() + '\'' +
                "}";
    }
}
