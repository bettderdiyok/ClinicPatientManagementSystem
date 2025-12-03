package clinic.repo;

import clinic.domain.Doctor;
import java.util.ArrayList;

public class DoctorRepository {
    private final ArrayList<Doctor> doctorArrayList = new ArrayList<>();

    public ArrayList<Doctor> getDoctorArrayList() {
        return doctorArrayList;
    }

    public boolean existsByNationalId(String nationalId) {
        for (Doctor doctor : doctorArrayList) {
            if(doctor.getNationalID().equalsIgnoreCase(nationalId)) {
                return true;
            }
        }
        return false;
    }

    public boolean existsBySystemId(int doctorId) {
        for (Doctor doctor : doctorArrayList) {
            if((doctor.getDoctorId() == doctorId)) {
                return true;
            }
        }
        return false;
    }

    public void add(Doctor doctor){
        doctorArrayList.add(doctor);
    }

    public void delete(int id) {
       doctorArrayList.removeIf(doctor -> doctor.getDoctorId() == id);
    }

    public void listDoctors(){
        for (Doctor doctor : doctorArrayList) {
            System.out.println(
                    "id : " + doctor.getDoctorId() +
                            " Fullname : " + doctor.getFullName() +
                            " Branch : " + doctor.getBranch()
            );
        }
    }

    public  Doctor findByDoctorId(int doctorId) {
        return doctorArrayList.stream()
                .filter(doctor -> doctor.getDoctorId() == doctorId)
                .findFirst()
                .orElse(null);
    }

}


