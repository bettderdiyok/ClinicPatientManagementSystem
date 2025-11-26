package clinic.repo;

import clinic.domain.Appointment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {
    private final ArrayList<Appointment> appointments = new ArrayList<>();
    public boolean existsAppointmentByDoctorId(int doctorId){
       return  appointments.stream().anyMatch(appointment -> appointment.getDoctorId() == doctorId);
    }



    public boolean existsPatientAndDateTime(int patientId, LocalDateTime time){
        return appointments.stream().anyMatch(appointment -> appointment.getPatientId() == patientId && appointment.getTime().equals(time));
     /* for (Appointment appointment : appointments){
            if (appointment.getDoctorId() == doctorId && appointment.getTime().equals(time)) {
                return true;
            }
        }
        return false;
        */
    }
    public void addAppointment(Appointment appointment){
        appointments.add(appointment);

    }

    public void deleteAppointment(int appointmentId){
       appointments.removeIf(appointment -> appointment.getAppointmentId() == appointmentId);
    }

    public void updateAppointment(){

    }



    public boolean existsDoctorAndDateTime(int doctorId, LocalDateTime time) {
        return appointments.stream().anyMatch(appointment -> appointment.getDoctorId() == doctorId && appointment.getTime().equals(time));
    }

    public List<Appointment> findAppointmentsByDoctorId(int doctorId){
        return appointments.stream().filter(appointment -> appointment.getDoctorId() == doctorId).toList();

    }

    public List<Appointment> findAppointmentsByPatientId(int patientId) {
        return appointments.stream().filter(appointment -> appointment.getPatientId() == patientId).toList();
    }

    public List<Appointment> allAppointments(){
        return  new ArrayList<>(appointments); //defensive copy
    }

    public Appointment findById(int appointmentId) {
        return appointments.stream()
                .filter(appointment -> appointment.getAppointmentId() == appointmentId)
                .findFirst()
                .orElse(null);
    }
}
