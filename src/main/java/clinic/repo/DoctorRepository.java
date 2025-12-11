package clinic.repo;

import clinic.domain.Doctor;
import clinic.util.IdGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository extends JsonBaseRepository<Doctor> {
    private final  List<Doctor> doctorList;
    private static final Path FILE_PATH = Path.of("data/doctors.json");
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public DoctorRepository() {
        super(FILE_PATH, GSON, new TypeToken<List<Doctor>>() {}.getType());
        this.doctorList = new ArrayList<>(readAllInternal());

        int maxId = doctorList.stream()
                        .mapToInt(Doctor:: getDoctorId)
                                .max()
                                        .orElse(0);

        IdGenerator.initDoctorId(maxId);
        saveAll();
    }

    private void saveAll() {
        writeAllInternal(doctorList);
    }

    public List<Doctor> getDoctorArrayList() {
        return doctorList;
    }

    public boolean existsByNationalId(String nationalId) {
        for (Doctor doctor : doctorList) {
            if(doctor.getNationalID().equalsIgnoreCase(nationalId)) {
                return true;
            }
        }
        return false;
    }

    public boolean existsBySystemId(int doctorId) {
        for (Doctor doctor : doctorList) {
            if((doctor.getDoctorId() == doctorId)) {
                return true;
            }
        }
        return false;
    }

    public void add(Doctor doctor){
        doctorList.add(doctor);
        saveAll();
    }

    public void delete(int id) {
       doctorList.removeIf(doctor -> doctor.getDoctorId() == id);
       saveAll();
    }

    public void update(){
       saveAll();
    }

    public void listDoctors(){
        for (Doctor doctor : doctorList) {
            System.out.println(
                    "id : " + doctor.getDoctorId() +
                            " Fullname : " + doctor.getFullName() +
                            " Branch : " + doctor.getBranch()
            );
        }
    }

    public  Doctor findByDoctorId(int doctorId) {
        return doctorList.stream()
                .filter(doctor -> doctor.getDoctorId() == doctorId)
                .findFirst()
                .orElse(null);
    }
}


