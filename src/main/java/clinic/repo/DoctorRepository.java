package clinic.repo;

import clinic.domain.Doctor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DoctorRepository {
    private ArrayList<Doctor> doctorArrayList = new ArrayList<>();
    private static final String FILE_PATH = "doctors.json";
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public DoctorRepository() {
        loadTheDoctors();
    }

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

    public void saveToDoctors() {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            String json = GSON.toJson(doctorArrayList);
            fileWriter.write(json);
        }catch (IOException e) {
           throw new RuntimeException("Failed to save appointments to JSON file.", e);

        }

    }

    public void loadTheDoctors(){
        File file = new File(FILE_PATH);
        if(!file.exists()){
            return;
        }

        try (FileReader fileReader = new FileReader(FILE_PATH)) {
            Doctor[] array = GSON.fromJson(fileReader, Doctor[].class);
            doctorArrayList = new ArrayList<>(Arrays.asList(array));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load appointments from JSON file.", e);
        }
    }

}


