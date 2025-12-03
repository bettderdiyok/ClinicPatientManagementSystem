package clinic.domain;
public abstract class Person {
    private String fullName;
    private final String nationalID;

    public Person(String fullName, String nationalID) {
        this.fullName = fullName;
        this.nationalID = nationalID;
    }

    public String getNationalID() {
        return nationalID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}
