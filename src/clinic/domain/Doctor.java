package clinic.domain;

public class Doctor extends Person {
    private  Branch branch;
    private int id;

    public Doctor(String nationalID, String fullName, Branch branch) {
        super(nationalID, fullName);
        this.branch = branch;
    }


    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }


}

