package clinic.repo;

import clinic.domain.Appointment;
import clinic.domain.Patient;
import java.util.ArrayList;

public class PatientRepository {
    ArrayList<Patient> patients = new ArrayList<>();
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

    public void addPatient(String fullname, String nationalId, int age, String complaint) {
        Patient newPatient = new Patient(fullname, nationalId, age, complaint);
        patients.add(newPatient);
    }

    public void deletePatient(int patientId) {
      patients.removeIf(patient -> patient.getPatientId() == patientId);
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
