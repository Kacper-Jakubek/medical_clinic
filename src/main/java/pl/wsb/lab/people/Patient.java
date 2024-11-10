package pl.wsb.lab.people;

import java.time.LocalDate;

public class Patient extends Person{
    public Patient(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String email, String pesel) {
        super(firstName, lastName, dateOfBirth, phoneNumber, email, pesel);
    }
}
