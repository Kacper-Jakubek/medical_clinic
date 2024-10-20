package pl.wsb.lab.people;

public class Patient extends Person{


    public Patient(String firsName, String lastName) {
        this.firstName = firsName;
        this.lastName = lastName;

    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
