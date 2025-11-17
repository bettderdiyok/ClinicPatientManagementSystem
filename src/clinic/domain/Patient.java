package clinic.domain;

public class Patient extends Person{
    private int id;
    private int age;

    public Patient(String nationalID, String fullName) {
        super(nationalID, fullName);
    }
}
