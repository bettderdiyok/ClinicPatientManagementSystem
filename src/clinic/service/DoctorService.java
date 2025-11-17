package clinic.service;
import clinic.domain.Doctor;
import clinic.exception.DuplicateDoctorException;
import clinic.repo.DoctorRepository;

public class DoctorService {
    private final DoctorRepository doctorRepository;
    //DI (Dependency Injection - Constructor Injection)
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void addDoctor(String nationalId, String fullname, String branch){
        if(isValidNationalId(nationalId)){
            if(doctorRepository.existsByNationalId(nationalId)) {
                throw new DuplicateDoctorException("Doctor already exists with this ID. ");
            }else {
                Doctor doctor = new Doctor(nationalId, fullname, branch);
                doctorRepository.add(doctor);
            }
        }

    }

    public void deleteDoctor(){

    }

    public void updateDoctor(){

    }
    public void listDoctors(){

    }

    public boolean isValidNationalId(String nationalId) {
        return nationalId != null && nationalId.matches("\\d{11}");

    }


}
