package clinic.repo;

import clinic.domain.Patient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PatientRepository {
    ArrayList<Patient> patients = new ArrayList<>();
    private static final String FILE_PATH = "patients.json";
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public PatientRepository() {
        loadFromJson();
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public boolean existsByNationalId(String nationalId) {
        for (Patient patient : patients) {
            if (patient.getNationalID().equalsIgnoreCase(nationalId)) {
                return true;
            }
        }
        return false;
    }

    public boolean existsBySystemId(int patientId) {
        for (Patient patient : patients) {
            if((patient.getPatientId() == patientId)) {
                return true;
            }
        }
        return false;
    }

    public Patient findByNationalId(String natioanlId) {
        return patients.stream()
                .filter(patient -> patient.getNationalID()
                        .equalsIgnoreCase(natioanlId))
                .findFirst()
                .orElse(null);
    }

    public void addPatient(String fullname, String nationalId, int age, String complaint) {
        Patient newPatient = new Patient(fullname, nationalId, age, complaint);
        patients.add(newPatient);
        saveToJson();
    }

    public void deletePatient(int patientId) {
      patients.removeIf(patient -> patient.getPatientId() == patientId);
      saveToJson();
        /*  ListIterator<Patient> iterator = patients.listIterator();
        while (iterator.hasNext()) {
            Patient currentPatient = iterator.next();
            if (currentPatient.getPatientId() == patientId) {
                iterator.remove();
                break;
            }
        }

       */
    }

    public void updatePatients(){
        saveToJson();
    }

    public void listPatient() {
        if (patients.isEmpty()) {
            System.out.println("There are no patients");
        } else {
            for (Patient patient : patients) {
                System.out.println("id : " + patient.getPatientId() +
                        " Fullname : " + patient.getFullName() + " " +
                        " NationalId : " + patient.getNationalID() +
                        " Age : " + patient.getAge() +
                        " Complaint : " + patient.getComplaint());
            }
        }
    }

    public Patient findPatient(int patientId) {
        for (Patient currentPatient : patients) {
            if (currentPatient.getPatientId() == patientId) {
                return currentPatient;
            }
        }
        throw new RuntimeException("Patient not found!");
    }

    public void saveToJson() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            String json = GSON.toJson(patients);
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save appointments to JSON file.", e);
        }
    }

    public void loadFromJson(){
        File file = new File(FILE_PATH);
        if(!file.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Patient[] array = GSON.fromJson(reader, Patient[].class);
            patients = new ArrayList<>(Arrays.asList(array));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
