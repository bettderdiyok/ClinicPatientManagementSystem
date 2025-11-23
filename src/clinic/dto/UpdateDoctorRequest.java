package clinic.dto;

import clinic.domain.Branch;

public class UpdateDoctorRequest {
    private String fullname;
    private Branch branch;

    public Branch getBranch() {
        return branch;
    }

    public String getFullname() {
        return fullname;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
