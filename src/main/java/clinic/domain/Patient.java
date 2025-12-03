package clinic.domain;

import clinic.util.IdGenerator;

public class Patient extends Person{
    private int patientId;
    private int age;
    private String complaint;

    public Patient(String fullName, String nationalID, int age, String complaint) {
        super(fullName, nationalID);
        this.age = age;
        this.complaint = complaint;
        this.patientId = IdGenerator.nextPatientID();
    }

    public String getComplaint() {
        return complaint;
    }

    public int getAge() {
        return age;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPatientId() {
        return patientId;
    }
}
