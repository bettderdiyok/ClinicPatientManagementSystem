package clinic.service;

import clinic.domain.Patient;
import clinic.dto.UpdatePatientRequest;
import clinic.exception.GuardianRequiredException;
import clinic.exception.InvalidComplaintException;
import clinic.exception.InvalidNationalIdException;
import clinic.repo.PatientRepository;

public class PatientService {
    private final PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    public void addPatient(String fullname, String nationalID, int age, boolean hasGuardian, String complaint){
        if(!isValidNationalID(nationalID)){
            throw new InvalidNationalIdException("Invalid national ID: " + nationalID );
        }

        if(!isValidName(fullname)) {
            System.out.println("Invalid fullname.");
            return;
        }

        if(patientRepository.existsByNationalId(nationalID)) {
            System.out.println("Patient is aldready added.");
            return;
        }

        if(!isValidAge(age) && !hasGuardian){
            throw new GuardianRequiredException("Guardian required for minor!");
        }

        if(!isValidComplaint(complaint)){
            throw new InvalidComplaintException("Complaint cannot be empty!");

        }

        patientRepository.addPatient(fullname, nationalID, age, complaint);
        System.out.println("Patient added successfully!");
    }

    public void deletePatient(String nationalId){
       patientRepository.deletePatient(nationalId);
        System.out.println("Patient deleted successfully!");


    }

    public void listPatients(){
        patientRepository.listPatient();

    }

    public void updatePatient(String nationalId, UpdatePatientRequest request) {
        Patient patient = patientRepository.findPatient(nationalId);
        if (request.getFullname() != null) {
            patient.setFullName(request.getFullname());
        }

        if (request.getAge() != null) {
            patient.setAge(request.getAge());
        }

        if (request.getComplaint() != null) {
            patient.setComplaint(request.getComplaint());
        }


    }

    public void searchPatient(){

    }

    public boolean isValidNationalID(String nationalId){
        return (nationalId != null && nationalId.matches("\\d{11}"));
    }

    public boolean isValidName(String name){
        return (name != null && name.matches("^[A-Za-zÇçĞğİıÖöŞşÜü\\s]+$"));
    }

    public boolean isValidAge(int age){
        return age >= 18;
    }

    public boolean isValidComplaint(String complaint){
        return complaint != null && !complaint.trim().isEmpty() &&  complaint.matches("^[A-Za-zÇçĞğİıÖöŞşÜü\\s]+$");
    }



}








