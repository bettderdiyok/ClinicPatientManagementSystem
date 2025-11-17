package clinic.domain;
public abstract class Person {
    private String nationalID;
    private String fullName;

    public Person(String nationalID, String fullName) {
        this.nationalID = nationalID;
        this.fullName = fullName;

    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
