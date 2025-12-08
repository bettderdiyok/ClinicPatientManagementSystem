package clinic.service;
import clinic.domain.*;
import clinic.dto.UpdateDoctorRequest;
import clinic.exception.*;
import clinic.repo.AppointmentRepository;
import clinic.repo.DoctorDayOffRepository;
import clinic.repo.DoctorRepository;
import clinic.util.NationalIdValidator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorDayOffRepository doctorDayOffRepository;
    private final AppointmentRepository appointmentRepository;

    //DI (Dependency Injection - Constructor Dependency Injection)
    public DoctorService(DoctorRepository doctorRepository, DoctorDayOffRepository doctorDayOffRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorDayOffRepository = doctorDayOffRepository;
        this.appointmentRepository = appointmentRepository;
    }


    public void addDoctor(String nationalId, String fullName, int branchNum) {
        if (!NationalIdValidator.isValidNationalId(nationalId)) {
            throw new InvalidNationalIdException("It is invalid id!");
        }

        if (!isValidFullName(fullName)) {
            throw new ValidationException("Invalid name.");
        }

        if (!isValidBranch(branchNum)) {
            throw new ValidationException("Invalid branch number.");
        }

        if (doctorRepository.existsByNationalId(nationalId)) {
            throw new DuplicateDoctorException("Doctor already exists with this national ID. ");
        }

        Doctor doctor = new Doctor(nationalId, fullName, Branch.values()[branchNum - 1]);
        doctorRepository.add(doctor);
    }

    public void deleteDoctor(int doctorId) {
        Doctor doctor = doctorRepository.findByDoctorId(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor not found.");
        }
        if (appointmentRepository.existsAppointmentByDoctorId(doctorId)) {
            throw new DoctorHasAppointmentsException("You can't delete this doctor because he has at least appointment.");
        }
        doctorRepository.delete(doctorId);
    }

    public void updateDoctor(int id, UpdateDoctorRequest request) {
        Doctor doctor = doctorRepository.findByDoctorId(id); // DoctorNotFoundException

        if(request.getFullname() != null){
            doctor.setFullName(request.getFullname());
        }

        if(request.getBranch() != null){
            doctor.setBranch(request.getBranch());
        }
        doctorRepository.update();

    }

    public void addDayOff(DoctorDayOff dayOff) {
        if (!doctorRepository.existsBySystemId(dayOff.getDoctorId())) {
            throw new DoctorNotFoundException("Doctor not found!");
        }

        if (dayOff.getDateTime().isBefore(LocalDate.now())) {
            throw new InvalidDayOffException("You cannot enter a past date as a day off.");
        }

        if (dayOff.getDateTime().getDayOfWeek() == DayOfWeek.SATURDAY || dayOff.getDateTime().getDayOfWeek() == DayOfWeek.SUNDAY) {
           throw  new InvalidDayOffException("Doctor does not work on weekends.");
        }

        if (doctorDayOffRepository.isDoctorOffOnDate(dayOff.getDoctorId(), dayOff.getDateTime())) {
           throw new InvalidDayOffException("Doctor already has a day off on this date.");
        }
        doctorDayOffRepository.addDayOff(dayOff);

        List<Appointment>  appointments = appointmentRepository.findAppointmentsByDoctorId(dayOff.getDoctorId());
        for (Appointment appointment : appointments) {
            appointment.setStatus(AppointmentStatus.CANCELED);
            appointmentRepository.updateAppointment(appointment);
        }

    }

    public void listDoctors() {
        if(doctorRepository.getDoctorArrayList().isEmpty()){
           throw new DoctorNotFoundException("No doctors found");
        }
        doctorRepository.listDoctors();
    }

    public List<DoctorDayOff> listDayOff(int doctorId) {
        if (!doctorRepository.existsBySystemId(doctorId)) {
            throw new DoctorNotFoundException("Doctor Not Found!");
        }
        return doctorDayOffRepository.findByDoctorId(doctorId);
    }

    public boolean isValidFullName(String fullName) {
        return fullName != null && fullName.matches("^[A-Za-zÇçĞğİıÖöŞşÜü\\s]+$");
    }

    public boolean isValidBranch(int branchNum) {
        Branch selectedBranch = Branch.values()[branchNum - 1];
        if (selectedBranch == null || selectedBranch.toString().trim().isEmpty()) {
            return false;
        }
        try {
            Branch.valueOf(selectedBranch.toString().trim().toUpperCase());
            return true;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Doctor findByDoctorId(int id){
        return doctorRepository.findByDoctorId(id);
    }

}




