package pl.wsb.lab.medicalclinic.shared.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateTimeParserTest {

    @Test
    void testParseDateWithValidFormats() {
        assertEquals(LocalDate.of(2023, 10, 15), DateTimeParser.parseDate("2023-10-15"));
        assertEquals(LocalDate.of(2023, 10, 15), DateTimeParser.parseDate("2023.10.15"));
        assertEquals(LocalDate.of(2023, 10, 15), DateTimeParser.parseDate("15.10.2023"));
        assertEquals(LocalDate.of(2023, 10, 15), DateTimeParser.parseDate("15-10-2023"));
    }

    @Test
    void testParseDateWithInvalidFormat() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parseDate("15/10/2023"));
    }

    @Test
    void testParseTimeWithValidFormats() {
        assertEquals(LocalTime.of(14, 30), DateTimeParser.parseTime("14:30"));
        assertEquals(LocalTime.of(14, 30), DateTimeParser.parseTime("14.30"));
    }

    @Test
    void testParseTimeWithInvalidFormat() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parseTime("14-30"));
    }

    @Test
    void testParseDateWithNull() {
        assertThrows(NullPointerException.class, () -> DateTimeParser.parseDate(null));
    }

    @Test
    void testParseTimeWithNull() {
        assertThrows(NullPointerException.class, () -> DateTimeParser.parseTime(null));
    }
}