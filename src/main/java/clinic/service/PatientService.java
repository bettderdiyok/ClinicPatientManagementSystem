package clinic.service;

import clinic.domain.Patient;
import clinic.dto.UpdatePatientRequest;
import clinic.exception.*;
import clinic.repo.DoctorRepository;
import clinic.repo.PatientRepository;
import clinic.util.NationalIdValidator;

public class PatientService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    public PatientService(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public void addPatient(String fullname, String nationalID, int age, boolean hasGuardian, String complaint){
        if(!NationalIdValidator.isValidNationalId(nationalID)){
            throw new InvalidNationalIdException("Invalid national ID.");
        }

        if(!isValidName(fullname)) {
            throw new ValidationException("Invalid fullname.");
        }

        if(patientRepository.existsByNationalId(nationalID)) {
           throw new DuplicatePatientException("Patient already exists.");
        }

        if(!isValidAge(age)) {
            throw new ValidationException("Invalid age.");
        }

        if(isMinorAge(age) && !hasGuardian){
            throw new GuardianRequiredException("Guardian required for minor!");
        }

        if(!isValidComplaint(complaint)){
            throw new ValidationException("Invalid complaint.");
        }

        if (doctorRepository.getDoctorArrayList().isEmpty()) {
            throw new ValidationException("There is no doctor in the system.");
        }

        patientRepository.addPatient(fullname, nationalID, age, complaint);

    }

    public void deletePatient(int patientId){
        Patient patient = patientRepository.findPatient(patientId);
        if(patientId <= 0){
            throw new ValidationException("Patient ID must be positive.");
        }

        if(patient == null) {
            throw new PatientNotFoundException("Patient not found.");
        }
         patientRepository.deletePatient(patientId);
    }

    public void listPatients(){
        patientRepository.listPatient();
    }

    public void updatePatient(int patientId, UpdatePatientRequest request) {
        Patient patient = patientRepository.findPatient(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("Patient not found");
        }

        if (request.getFullname() != null) {
            patient.setFullName(request.getFullname());
        }

        if (request.getAge() != null) {
            patient.setAge(request.getAge());
        }

        if (request.getComplaint() != null) {
            patient.setComplaint(request.getComplaint());
        }
        patientRepository.updatePatients();
    }

    public boolean isValidName(String name){
        return (name != null && name.matches("^[A-Za-zÇçĞğİıÖöŞşÜü\\s]+$"));
    }

    public boolean isValidAge(int age){
        return age > 0 && age <= 120;
    }

    public boolean isMinorAge(int age){
        return age < 18;
    }

    public boolean isValidComplaint(String complaint){
        return complaint != null && !complaint.trim().isEmpty() &&  complaint.matches("^[A-Za-zÇçĞğİıÖöŞşÜü\\s]+$");
    }

    public boolean isPatientListEmpty(){
        return patientRepository.getPatients().isEmpty();
    }
}








