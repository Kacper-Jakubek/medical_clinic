package pl.wsb.lab.medicalclinic.shared.util;

import pl.wsb.lab.medicalclinic.doctor.MedicalSpecialty;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SpecialtiesParser {

    // Private constructor to prevent instantiation
    private SpecialtiesParser() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }


    private static Set<String> splitStringToSet(String input) {
        String[] items = input.split(",");
        return new HashSet<>(Arrays.asList(items));
    }

    /**
     * Parses a comma-separated string of medical specialties and returns a set of valid MedicalSpecialty enums.
     *
     * @param input the comma-separated string of medical specialties
     * @return a set of MedicalSpecialty enums that match the input strings
     */
    public static Set<MedicalSpecialty> specialtiesFromString(String input) {
        if (input == null) {
            return Collections.emptySet();
        }

        Set<String> specialtiesStrings = splitStringToSet(input);
        Set<MedicalSpecialty> specialties = new HashSet<>();

        for (String specialtyString : specialtiesStrings) {
            Arrays.stream(MedicalSpecialty.values())
                    .filter(specialty -> specialty.name().equalsIgnoreCase(specialtyString.trim()))
                    .findFirst()
                    .ifPresent(specialties::add);
        }
        return specialties;
    }
}
