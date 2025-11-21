package clinic.cli;

import clinic.domain.Branch;
import clinic.domain.DayOffType;
import clinic.domain.DoctorDayOff;
import clinic.exception.DoctorNotFoundException;
import clinic.exception.DuplicateDoctorException;
import clinic.exception.InvalidNationalIdException;
import clinic.service.DoctorService;

import java.time.LocalDate;
import java.util.Scanner;

public class DoctorMenu {
    Scanner input = new Scanner(System.in);
    private final  DoctorService doctorService;

    public DoctorMenu(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public void showTheDoctorMenu() {
        int choice;
        System.out.println("------MENU-----");
        boolean isTrue = true;
        while (isTrue) {
            showTheMenu();
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter doctor name: ");
                        String name = input.nextLine();

                        System.out.print("Enter national ID: ");
                        String nationalId = input.next();

                        listBranch();

                        System.out.print("Enter branch: ");
                        int branch = input.nextInt();
                        doctorService.addDoctor(nationalId, name, branch);
                        System.out.println("Doctor added successfully!");
                    } catch(DuplicateDoctorException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Returning to menu...");
                } catch (InvalidNationalIdException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter national ID: ");
                    String nationalId = input.next();
                    try {
                        doctorService.deleteDoctor(nationalId);
                        System.out.println("Doctor deleted successfully.");
                    } catch (InvalidNationalIdException | DoctorNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter national ID: ");
                    nationalId = input.next();
                    input.nextLine();

                    System.out.print("Enter doctor name: ");
                    String name = input.nextLine();

                    listBranch();

                    System.out.print("Enter branch: ");
                    int branch = input.nextInt();

                    doctorService.updateDoctor(nationalId, name, branch);

                    break;
                case 4:
                    doctorService.listDoctors();
                    break;
                case 5:
                    showTheMenu();
                    break;
                case 6:
                    isTrue = false;
                case 7:
                    System.out.println("---DOCTOR LIST---");
                    doctorService.listDoctors();
                    System.out.print("Enter Doctor id: ");
                    int doctorId= input.nextInt();

                    if(!doctorService.existsBySystemId(doctorId)){
                        System.out.println("Doctor not found!");
                        break;
                    }

                    System.out.print("Enter day off date (YYYY-MM-DD): ");
                    String strDate = input.next();
                    LocalDate date;

                    try {
                        date = LocalDate.parse(strDate);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Example : 2025-11-13");
                        break;
                    }

                    listDayOffType();
                    System.out.println("Enter day off type : ");
                    int choiceType = input.nextInt();
                    DayOffType[] dayOffType = DayOffType.values();
                    DayOffType selectedType = dayOffType[choiceType-1];

                    System.out.println("Enter a note : ");
                    String note = input.nextLine();

                    DoctorDayOff dayOff = new DoctorDayOff(doctorId, date, selectedType, note);
            }
        }
    }

    public static void showTheMenu(){
        System.out.println("1-Add Doctor\n" +
                "2-Delete Doctor\n" +
                "3-Update Doctor\n" +
                "4-List Doctors\n" +
                "5-Menu Again\n" +
                "6-Back to Main Menu\n" +
                "7-Add Doctor Day Off\n"
        );
    }

    public static void listDayOffType(){
        DayOffType[] types = DayOffType.values();
        for (int i=0; i<types.length; i++) {
            System.out.println((i+1) + ")" + types[i]);
        }
    }

    public void listBranch(){
        Branch[] branches = Branch.values();
        for(int i=0; i<branches.length; i++) {
            System.out.println((i+1) + " - " + branches[i]);
        }
    }
}



