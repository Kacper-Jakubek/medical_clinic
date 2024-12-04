package pl.wsb.lab.utils;

import pl.wsb.lab.medicalclinic.domain.doctor.model.MedicalSpecialty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecialtiesParser {
    private static final Logger logger = Logger.getLogger(SpecialtiesParser.class.getName());

    private static ArrayList<String> splitStringToList(String input) {
        String[] items = input.split(",");
        return new ArrayList<>(Arrays.asList(items));
    }

    public static List<MedicalSpecialty> parseSpecialties(String input) {
        ArrayList<String> specialtiesStrings = splitStringToList(input);
        ArrayList<MedicalSpecialty> specialties = new ArrayList<>();
        for (String specialtyString : specialtiesStrings) {
            try {
                specialties.add(MedicalSpecialty.valueOf(specialtyString.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, String.format("Unknown specialty: %s", specialtyString));
            }
        }
        return specialties;
    }
}
