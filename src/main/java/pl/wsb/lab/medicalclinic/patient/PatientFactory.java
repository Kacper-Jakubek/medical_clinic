package pl.wsb.lab.medicalclinic.patient;

import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;
import pl.wsb.lab.medicalclinic.shared.util.PersonValidator;

import java.time.LocalDate;

public class PatientFactory {

    // Private constructor to prevent instantiation
    private PatientFactory() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Patient createPatient(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            ContactInfo contactInfo,
            String pesel
    ) {
        PersonValidator.validatePersonData(firstName, lastName, dateOfBirth, contactInfo, pesel);
        return new Patient(firstName, lastName, dateOfBirth, contactInfo, pesel);
    }

    public static Patient createPatient(
            String firstName,
            String lastName,
            String dateOfBirth,
            String phoneNumber,
            String email,
            String pesel
    ) {
        LocalDate dob = PersonValidator.dateFromString(dateOfBirth);
        ContactInfo contactInfo = new ContactInfo(phoneNumber, email);
        PersonValidator.validatePersonData(firstName, lastName, dob, contactInfo, pesel);
        return createPatient(firstName, lastName, dob, contactInfo, pesel);
    }
}