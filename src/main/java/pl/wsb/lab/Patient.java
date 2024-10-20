package pl.wsb.lab;

public class Patient {

    private String firstName;
    private String lastName;


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
