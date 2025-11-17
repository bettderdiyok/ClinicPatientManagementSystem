package clinic.repo;

import clinic.domain.Doctor;

import java.util.ArrayList;


public class DoctorRepository {
    private final ArrayList<Doctor> doctorArrayList = new ArrayList<>();

    public boolean existsByNationalId(String nationalId) {
        for (Doctor doctor : doctorArrayList) {
            if(doctor.getNationalID().equalsIgnoreCase(nationalId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSameBranch(String branch){
        for (Doctor doctor : doctorArrayList) {
                return doctor.getBranch().equalsIgnoreCase(branch) ;
        }
        return false;
    }



    public void add(Doctor doctor){
        doctorArrayList.add(doctor);
    }
}


