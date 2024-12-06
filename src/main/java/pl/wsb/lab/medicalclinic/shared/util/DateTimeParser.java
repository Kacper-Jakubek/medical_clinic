package pl.wsb.lab.medicalclinic.shared.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeParser {

    private DateTimeParser() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            .toFormatter(Locale.ENGLISH);

    public static LocalDate parseDate(String date) throws DateTimeParseException {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("HH:mm"))
            .appendOptional(DateTimeFormatter.ofPattern("HH.mm"))
            .toFormatter(Locale.ENGLISH);

    public static LocalTime parseTime(String time) throws DateTimeParseException {
        return LocalTime.parse(time, TIME_FORMATTER);
    }
}