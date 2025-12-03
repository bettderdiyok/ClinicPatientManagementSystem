package clinic.domain;

import clinic.util.IdGenerator;

public class Doctor extends Person {
    private  Branch branch;
    private int doctorId;

    public Doctor(String fullName, String nationalID, Branch branch) {
        super(nationalID, fullName);
        this.branch = branch;
        this.doctorId = IdGenerator.nextDoctorID();
    }

    public Branch getBranch() {
        return branch;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}

