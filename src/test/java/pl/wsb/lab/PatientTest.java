package pl.wsb.lab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.wsb.lab.people.Patient;

public class PatientTest {

    @Test
    void checkIfFullNameIsReturnedCorrectly() {

         // given
        Patient givenPatient = new Patient("John", "White");

        // when
        String fullNameUnderTests = givenPatient.getFullName();

        // then
        String expectedFullName = "John White";
        Assertions.assertEquals(expectedFullName, fullNameUnderTests);
    }
}
