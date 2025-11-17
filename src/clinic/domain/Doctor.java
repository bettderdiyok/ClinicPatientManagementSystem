package clinic.domain;

public class Doctor extends Person {
    private String branch;
    private int id;

    public Doctor(String nationalID, String fullName, String branch) {
        super(nationalID, fullName);
        this.branch = branch;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
