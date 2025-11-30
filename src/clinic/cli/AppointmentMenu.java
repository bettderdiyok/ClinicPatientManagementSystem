package clinic.cli;

import clinic.domain.Appointment;
import clinic.domain.Doctor;
import clinic.domain.Patient;
import clinic.dto.UpdateAppointmentRequest;
import clinic.exception.*;
import clinic.service.AppointmentService;
import clinic.service.DoctorService;
import clinic.service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AppointmentMenu {
    Scanner input = new Scanner(System.in);
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentMenu(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public void showTheAppointmentMenu() {
        int choice;
        boolean isTrue = true;
        while (isTrue) {
            System.out.println("------MENU-----");
            showTheMenu();
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    try {
                        System.out.println("----- DOCTOR LIST -----");
                        doctorService.listDoctors();

                        System.out.print("Enter Doctor ID : ");
                        int doctorID = input.nextInt();

                        System.out.println("----- PATIENT LIST ------");
                        patientService.listPatients();
                        if(patientService.isPatientListEmpty()) {
                            System.out.println("Add a patient first.");
                            break;
                        }

                        System.out.print("Enter Patient ID : ");
                        int patientID = input.nextInt();

                        System.out.println("Enter Appointment date and time : ");
                        System.out.print("Date : (YYYY-MM-DD)");
                        String strDate = input.next();
                        LocalDate date;
                        try {
                            date = LocalDate.parse(strDate);
                        } catch (Exception e) {
                            System.out.println("Invalid date format.");
                            break;
                        }
                        System.out.print("Hour (09-17): ");
                        int hour = input.nextInt();
                        input.nextLine();

                        System.out.print("Minute :  (00-15-30-45):");
                        int minute = input.nextInt();
                        input.nextLine();

                        LocalDateTime time = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), hour, minute);
                        //getMonth ->ENUM getMonthValue()-> int
                        appointmentService.createAppointment(doctorID, patientID, time);
                        System.out.println("Appointment created successfully!");
                    } catch (DoctorNotFoundException |
                             PatientNotFoundException |
                             InvalidAppointmentTimeException |
                             DoctorAppointmentAlreadyBookedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    listAppointmentMenu();
                    try {
                        System.out.println("Enter appointment id : ");
                        int appointmentId = input.nextInt();

                        appointmentService.deleteAppointment(appointmentId);
                        System.out.println("Appointment deleted.");
                    } catch (AppointmentNotFoundException | InvalidAppointmentTimeException  e ) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    listAppointmentMenu();
                    try {
                        System.out.print("Enter Appointment ID to update: ");
                        int appointmentId = input.nextInt();
                        input.nextLine();

                        System.out.println("New doctor ID (Leave it blank if you don’t want to change it.): ");
                        String newId = input.nextLine();

                        System.out.println("New date YYYY-MM-DD (Leave it blank if you don’t want to change it.): ");
                        String newTime = input.nextLine();

                        System.out.println("New time hour (Leave it blank if you don’t want to change it.): ");
                        String newHour = input.nextLine();

                        System.out.println("New time minute (00-15-30-45) (Leave it blank if you don’t want to change it.): ");
                        String newMinute = input.nextLine();

                        UpdateAppointmentRequest request = new UpdateAppointmentRequest();

                        if(!newId.isBlank()) {
                            int newDoctorId = Integer.parseInt(newId);
                            request.setDoctorId(newDoctorId);
                        }

                        boolean anyTimeFieldFilled =
                                !newTime.isBlank() || !newHour.isBlank() || !newMinute.isBlank();

                        if (anyTimeFieldFilled) {
                            if (newTime.isBlank() || newHour.isBlank() || newMinute.isBlank()) {
                                System.out.println("If you want to change the appointment time, you must enter date, hour and minute.");
                                break;
                            }

                            LocalDate date;
                            try {
                                date = LocalDate.parse(newTime);
                            } catch (Exception e) {
                                System.out.println("Invalid date format. Use YYYY-MM-DD.");
                                break;
                            }

                            int hour = Integer.parseInt(newHour);
                            int minute = Integer.parseInt(newMinute);

                            LocalDateTime time = LocalDateTime.of(
                                    date.getYear(),
                                    date.getMonthValue(),
                                    date.getDayOfMonth(),
                                    hour,
                                    minute);

                            request.setTime(time);
                        }

                        appointmentService.updateAppointment(appointmentId, request);
                        System.out.println("Appointment updated.");

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid date.");
                    } catch (AppointmentNotFoundException |
                    DoctorNotFoundException |
                    InvalidAppointmentTimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    listAppointmentMenu();
                    break;
                case 5:
                    showTheMenu();
                    break;
                case 6:
                    isTrue = false;
            }
        }
    }

    public static void showTheMenu() {
        System.out.println("1-Create Appointment\n" +
                "2-Cancel Appointment \n" +
                "3-Update Appointment\n" +
                "4-List\n" +
                "5-Menu Again\n" +
                "6-Back to Main Menu\n"
        );
    }

      private void listAppointmentMenu() {
        System.out.println("----- LIST APPOINTMENTS -----");
        System.out.println("1 - List all appointments");
        System.out.println("2 - List appointments by doctor");
        System.out.println("3 - List appointments by patient");
        System.out.print("Your choice: ");

        int listChoice = input.nextInt();
        input.nextLine();

        switch (listChoice) {
            case 1:
                listAllAppointments();
                break;
            case 2:
                listAppointmentsByDoctor();
                break;
            case 3:
                listAppointmentsByPatient();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void listAppointmentsByDoctor() {
        System.out.println("----- DOCTOR LIST -----");
        doctorService.listDoctors();
        try {
            System.out.print("Enter Doctor ID: ");
            int doctorId = input.nextInt();
            input.nextLine();

            Doctor doctor = appointmentService.getDoctor(doctorId);

            List<Appointment> appointments = appointmentService.listAppointmentsByDoctorId(doctorId);
            appointments.forEach(appointment -> {
                Patient patient = appointmentService.getPatient(appointment.getPatientId());
                System.out.println("Appointment ID : " + appointment.getAppointmentId() +
                        " Doctor : " + doctor.getFullName() +
                        " Patient : " + patient.getFullName() +
                        " Time : " + appointment.getTime());
            });

        } catch (DoctorNotFoundException | ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listAppointmentsByPatient() {
        System.out.println("----- PATIENT LIST ------");
        patientService.listPatients();
        try {
            System.out.print("Enter patient ID : ");
            int patientId = input.nextInt();
            input.nextLine();
            Patient patient = appointmentService.getPatient(patientId);
            List<Appointment> patientAppointments = appointmentService.listAppointmentsByPatientId(patientId);

            patientAppointments.forEach(appointment -> {
                Doctor doctor = appointmentService.getDoctor(appointment.getDoctorId());
                System.out.println("Appointment ID : " + appointment.getAppointmentId() +
                        " Patient : " + patient.getFullName() +
                        " Doctor : " + doctor.getFullName() +
                        " Time : " + appointment.getTime());
            });
        } catch (PatientNotFoundException | AppointmentNotFoundException e) {
            System.out.println(e.getMessage());

        }

    }

    private void listAllAppointments(){
        try {
            List<Appointment> appointments = appointmentService.listAllAppointmentsSortedByTime();

            for (Appointment appointment : appointments) {
                Doctor doctor = appointmentService.getDoctor(appointment.getDoctorId());
                Patient patient =  appointmentService.getPatient(appointment.getPatientId());

                System.out.println("Appointment ID : " + appointment.getAppointmentId() +
                        " Time : " + appointment.getTime() +
                        " Doctor : " + doctor.getFullName() +
                        " Patient : " + patient.getFullName());
            }
        } catch (AppointmentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

