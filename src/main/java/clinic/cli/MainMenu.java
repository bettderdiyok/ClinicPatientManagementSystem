package clinic.cli;

import clinic.exception.NotFoundException;
import clinic.exception.ValidationException;
import clinic.repo.AppointmentRepository;
import clinic.repo.DoctorDayOffRepository;
import clinic.repo.DoctorRepository;
import clinic.repo.PatientRepository;
import clinic.service.AppointmentService;
import clinic.service.DoctorService;
import clinic.service.PatientAppointmentCheckInService;
import clinic.service.PatientService;

import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isTrue = true;
        int choice;
        DoctorRepository doctorRepository = new DoctorRepository();
        DoctorDayOffRepository doctorDayOffRepository = new DoctorDayOffRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        PatientRepository patientRepository = new PatientRepository();


        DoctorService doctorService = new DoctorService(doctorRepository, doctorDayOffRepository, appointmentRepository );
        PatientService patientService = new PatientService(patientRepository, doctorRepository);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository, doctorRepository, patientRepository, doctorDayOffRepository);
        PatientAppointmentCheckInService appointmentCheckInService = new PatientAppointmentCheckInService(patientRepository, appointmentRepository);

        AppointmentMenu appointmentMenu = new AppointmentMenu(appointmentService, doctorService, patientService);
        DoctorMenu doctorMenu = new DoctorMenu(doctorService);
        PatientMenu patientMenu = new PatientMenu(patientService);


        System.out.println("Welcome to the Clinic Patient Management System");
        while(isTrue){
            System.out.println("----MENU----");
            showTheMenu();
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    patientMenu.showThePatientMenu();
                    break;
                case 2:
                    doctorMenu.showTheDoctorMenu();
                    break;
                case 3:
                    appointmentMenu.showTheAppointmentMenu();
                    break;
                case 4:
                    System.out.print("Enter patient nationalId : ");
                    String nationalId = input.nextLine();
                    try {
                        appointmentCheckInService.checkIn(nationalId);
                        System.out.println("Check-in completed.");
                    } catch (ValidationException | NotFoundException e) {
                        System.out.println(e.getMessage());
                    }


                case 0:
                    isTrue = false;
                    break;
            }
        }
    }

    public static void showTheMenu(){
        System.out.println("""
                0-Exit
                1-Patient Management
                2-Doctor Management
                3-Appointment
                4-Patient Appointment Check-In
                """
               );
    }
}
