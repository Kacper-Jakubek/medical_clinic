package pl.wsb.lab.medicalclinic.doctor;

import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;
import pl.wsb.lab.medicalclinic.shared.util.PersonValidator;
import pl.wsb.lab.medicalclinic.shared.util.SpecialtiesParser;

import java.time.LocalDate;
import java.util.Collection;

public class DoctorFactory {

    // Private constructor to prevent instantiation
    private DoctorFactory() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Doctor createDoctor(String firstName, String lastName, LocalDate dateOfBirth, ContactInfo contactInfo, String pesel, Collection<MedicalSpecialty> specialties) {
        PersonValidator.validatePersonData(firstName, lastName, dateOfBirth, contactInfo, pesel);
        return new Doctor(firstName, lastName, dateOfBirth, contactInfo, pesel, specialties);
    }

    public static Doctor createDoctor(String firstName, String lastName, String dateOfBirth, String phoneNumber, String email, String pesel, String medicalSpecialties) {
        LocalDate dob = PersonValidator.dateFromString(dateOfBirth);
        ContactInfo contactInfo = new ContactInfo(phoneNumber, email);
        Collection<MedicalSpecialty> specialties = SpecialtiesParser.specialtiesFromString(medicalSpecialties);
        PersonValidator.validatePersonData(firstName, lastName, dob, contactInfo, pesel);
        return createDoctor(firstName, lastName, dob, contactInfo, pesel, specialties);
    }


}