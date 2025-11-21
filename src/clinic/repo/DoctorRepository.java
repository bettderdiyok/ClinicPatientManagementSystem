package clinic.repo;

import clinic.domain.Branch;
import clinic.domain.Doctor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;


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

    public void delete(String nationalId) {
        Iterator<Doctor> iterator = doctorArrayList.iterator();
         while(iterator.hasNext()) {
             Doctor doctor = iterator.next();
             if(doctor.getNationalID().equals(nationalId)) {
                 iterator.remove();
                 System.out.println("Doctor deleted.");
                 return;
             }
         }

    }

    public void updateDoctor(String id, String newFullName, int newBranch){
        ListIterator<Doctor> iterator = doctorArrayList.listIterator();
        while (iterator.hasNext()) {
            Doctor current = iterator.next();
            if(current.getNationalID().equalsIgnoreCase(id)){
                Doctor updatedDoctor = new Doctor(current.getNationalID(),
                        newFullName,
                        Branch.values()[newBranch]);
                iterator.set(updatedDoctor);
                System.out.println("The doctor updated!");
                return;
            }
        }
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
}


