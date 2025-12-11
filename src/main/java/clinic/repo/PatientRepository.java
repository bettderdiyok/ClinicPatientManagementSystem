package clinic.repo;

import clinic.domain.Patient;
import clinic.util.IdGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository extends JsonBaseRepository<Patient> {
    List<Patient> patients;
    private static final Path FILE_PATH = Path.of("data/patients.json");
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public PatientRepository() {
        super(FILE_PATH, GSON, new TypeToken<List<Patient>>() {}.getType());
        this.patients = new ArrayList<>(readAllInternal());

        int maxId = patients.stream()
                .mapToInt(Patient::getPatientId)
                .max()
                .orElse(0);

        IdGenerator.initPatientId(maxId);
        saveAll();
    }

    private void saveAll() {
        writeAllInternal(patients);
    }

    public List<Patient> getPatients() {
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
        saveAll();

    }

    public void deletePatient(int patientId) {
      patients.removeIf(patient -> patient.getPatientId() == patientId);
      saveAll();

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
        saveAll();
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

}
