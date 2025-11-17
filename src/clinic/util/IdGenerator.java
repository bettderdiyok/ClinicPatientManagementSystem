package clinic.util;

public class IdGenerator {
    private static int patientCounter = 0;
    private static int doctorCounter = 0;
    private static int appointmentCounter = 0;

    public static int nextPatientID(){
        return ++patientCounter;
    }

    public static int nextDoctorID(){
        return ++doctorCounter;
    }
    public static int nextAppointmentID(){
        return ++appointmentCounter;
    }
}
