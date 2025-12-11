package clinic.repo;

import clinic.domain.Appointment;
import clinic.domain.AppointmentStatus;
import clinic.util.IdGenerator;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AppointmentRepository extends JsonBaseRepository<Appointment> {
    private static final Path FILE_PATH = Path.of("appointments.json");
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                            LocalDateTime.parse(json.getAsString()))
            .create();

    private final List<Appointment> appointments;

    public AppointmentRepository() {
        super(FILE_PATH,
                GSON,
                new TypeToken<List<Appointment>>() {}.getType());

        this.appointments = new ArrayList<>(readAllInternal());
        int maxId = appointments.stream()
                .mapToInt(Appointment::getAppointmentId)
                .max()
                .orElse(0);

        IdGenerator.initAppointmentId(maxId);

        markPatAppointmentsAsMissed();
        saveAll();
    }

    private void saveAll() {
        writeAllInternal(appointments);
    }

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
    public Optional<Appointment> findLastAppointmentForPatientAndDoctor(int patientId, int doctorId) {
        return appointments.stream()
                .filter(appointment ->
                                appointment.getPatientId() == patientId &&
                                appointment.getDoctorId() == doctorId).max(Comparator.comparing(Appointment ::getTime));
    }

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
        saveAll();

    }

    public void updateAppointment(Appointment appointment) {
        saveAll();
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
        return new ArrayList<>(appointments); //defensive copy
    }

    public Appointment findById(int appointmentId) {
        return appointments.stream()
                .filter(appointment -> appointment.getAppointmentId() == appointmentId)
                .findFirst()
                .orElse(null);
    }

    public boolean existsByDoctorPatientAndDate(int doctorId, int patientId, LocalDate date){
        return appointments.stream()
                .anyMatch(appointment ->
                        appointment.getDoctorId() == doctorId &&
                        appointment.getPatientId() == patientId &&
                                appointment.getTime().toLocalDate().equals(date)
                );
    }

    private void markPatAppointmentsAsMissed() {
        LocalDateTime now = LocalDateTime.now();

        appointments.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.BOOKED)
                .filter(appointment -> appointment.getTime().isBefore(now))
                .forEach(appointment -> appointment.setStatus(AppointmentStatus.MISSED));
    }

}
