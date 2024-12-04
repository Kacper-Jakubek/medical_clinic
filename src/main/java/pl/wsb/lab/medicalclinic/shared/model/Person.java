package pl.wsb.lab.medicalclinic.shared.model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Person {
    private String firstName;
    private String lastName;
    private final LocalDate dateOfBirth;
    private ContactInfo contactInfo;
    private final String pesel;

    protected Person(String firstName, String lastName, LocalDate dateOfBirth, ContactInfo contactInfo, String pesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.contactInfo = contactInfo;
        this.pesel = pesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }


    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getPesel() {
        return pesel;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        return LocalDate.now().getYear() - this.dateOfBirth.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(pesel, person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pesel);
    }
}
