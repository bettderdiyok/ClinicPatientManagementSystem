package clinic.repo;

import clinic.domain.Appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AppointmentRepository {
    private final ArrayList<Appointment> appointments = new ArrayList<>();

    public boolean existsDoctorAndDataTime(int doctorId, LocalDateTime time){

        return appointments.stream().anyMatch(appointment ->
                        appointment.getDoctorId() == doctorId &&
                                appointment.getTime().equals(time)
                );
       /* for (Appointment appointment : appointments){
            if (appointment.getDoctorId() == doctorId && appointment.getTime().equals(time)) {
                return true;
            }

        }
        return false;

        */
    }

    public boolean existsPatientAndDateTime(int patientId, LocalDateTime time){
        return appointments.stream().anyMatch(appointment -> appointment.getPatientId() == patientId && appointment.getTime().equals(time));

    }
    public void addAppointment(){

    }

    public void cancelAppointment(){

    }

    public void updateAppointment(){

    }



    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
}
