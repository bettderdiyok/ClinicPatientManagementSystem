package clinic.dto;

public class UpdatePatientRequest {
    private String fullname;
    private Integer age;
    private String complaint;

    public Integer getAge() {
        return age;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getFullname() {
        return fullname;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
