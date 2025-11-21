package clinic.service;
import clinic.domain.Branch;
import clinic.domain.Doctor;
import clinic.exception.DoctorNotFoundException;
import clinic.exception.DuplicateDoctorException;
import clinic.exception.InvalidNationalIdException;
import clinic.repo.DoctorRepository;

public class DoctorService {
    private final DoctorRepository doctorRepository;
    //DI (Dependency Injection - Constructor Dependency Injection)
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public boolean existsBySystemId(int doctorID){
        return this.doctorRepository.existsBySystemId(doctorID); //Delegation
    }
    public void addDoctor(String nationalId, String fullname, int branchNum){
        if(!isValidNationalId(nationalId)) {
            throw new InvalidNationalIdException("It is invalid id!");
        }

        if(!isValidFullname(fullname)) {
            System.out.println("Full name is invalid");
            return;
        }

        if(!isValidBranch(branchNum)) {
            System.out.println("The branch is invalid!! ");
            return;
        }

        if(doctorRepository.existsByNationalId(nationalId)) {
            throw new DuplicateDoctorException("Doctor already exists with this ID. ");
        }

        Doctor doctor = new Doctor(nationalId, fullname, Branch.values()[branchNum-1]);
        doctorRepository.add(doctor);


    }

    public void deleteDoctor(String nationalId){
        if (isValidNationalId(nationalId)) {
            throw new InvalidNationalIdException("Invalid national ID");
        }

        if(!doctorRepository.existsByNationalId(nationalId)){
            throw new DoctorNotFoundException("No doctor found with th,s national ID.");
        }

        doctorRepository.delete(nationalId);
        }

    public void updateDoctor(String id, String name, int branch){
        doctorRepository.updateDoctor(id, name, branch);
    }

    public void listDoctors(){
        doctorRepository.listDoctors();
    }

    public boolean isValidNationalId(String nationalId) {
        return nationalId != null && nationalId.matches("\\d{11}");

    }

    public boolean isValidFullname(String fullname){
        return fullname != null && fullname.matches("^[A-Za-zÇçĞğİıÖöŞşÜü\\s]+$");

    }

    public boolean isValidBranch(int branchNum){
        Branch selectedBranch = Branch.values()[branchNum-1];
       if(selectedBranch == null || selectedBranch.toString().trim().isEmpty()){
           return false;
       }
        try {
             Branch.valueOf(selectedBranch.toString().trim().toUpperCase());
             return true;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
