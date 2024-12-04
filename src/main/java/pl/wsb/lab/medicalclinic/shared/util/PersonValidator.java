package pl.wsb.lab.medicalclinic.shared.util;

import pl.wsb.lab.medicalclinic.shared.model.ContactInfo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Utility class for validating personal data.
 * This class provides methods to validate personal information such as name, date of birth, contact information, and PESEL number.
 * It also includes a method to parse a date from a string.
 * The class cannot be instantiated.
 */
public class PersonValidator {

    // Private constructor to prevent instantiation
    private PersonValidator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Validates the provided personal data for a person.
     *
     * @param firstName the first name of the person, must not be null or empty
     * @param lastName the last name of the person, must not be null or empty
     * @param dateOfBirth the date of birth of the person, must not be null or in the future
     * @param contactInfo the contact information of the person, must not be null and must contain a non-empty phone number
     * @param pesel the PESEL number of the person, must be exactly 11 characters long
     * @throws IllegalArgumentException if any of the validation checks fail
     */
    public static void validatePersonData(String firstName, String lastName, LocalDate dateOfBirth, ContactInfo contactInfo, String pesel) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be null or in the future.");
        }
        if (contactInfo == null || contactInfo.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (pesel == null || pesel.length() != 11) {
            throw new IllegalArgumentException("Provided PESEL is not valid.");
        }
    }

    public static LocalDate dateFromString(String date) {
        LocalDate dob;
        try {
            dob = DateParser.parseDate(date);
            return dob;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Provided date of birth is not in the correct format. Accepted formats are: \"yyyy-MM-dd\", \"yyyy.MM.dd\", \"dd.MM.yyyy\", \"dd-MM-yyyy\"", e);
        }
    }
}
