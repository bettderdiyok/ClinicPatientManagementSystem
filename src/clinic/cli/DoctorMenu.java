package clinic.cli;

import clinic.domain.Branch;
import clinic.domain.DayOffType;
import clinic.domain.DoctorDayOff;
import clinic.dto.UpdateDoctorRequest;
import clinic.exception.*;
import clinic.service.DoctorService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class DoctorMenu {
    Scanner input = new Scanner(System.in);
    private final  DoctorService doctorService;
    //DI - Constructor Injection
    public DoctorMenu(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public void showTheDoctorMenu() {
        int choice;
        boolean isTrue = true;
        while (isTrue) {
            System.out.println("------MENU-----");
            showTheMenu();
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            input.nextLine();
            try {
                int doctorId;
                switch (choice) {
                    case 1:
                            System.out.print("Enter doctor name: ");
                            String name = input.nextLine();

                            System.out.print("Enter national ID: ");
                            String nationalId = input.next();

                            listBranch();

                            System.out.print("Enter branch: ");
                            int branch = input.nextInt();
                            doctorService.addDoctor(nationalId, name, branch);
                            System.out.println("Doctor added successfully!");
                        break;
                    case 2:
                        System.out.println("----- DOCTOR LIST ----");
                        doctorService.listDoctors();

                        System.out.print("Enter Doctor ID to delete : ");
                        doctorId = input.nextInt();
                        input.nextLine();

                        doctorService.deleteDoctor(doctorId);
                        System.out.println("Doctor deleted successfully.");
                        break;
                    case 3:
                        System.out.println("----- DOCTOR LIST ----");
                        doctorService.listDoctors();

                        System.out.print("Enter Doctor ID: ");
                        doctorId = input.nextInt();
                        input.nextLine();

                        System.out.print("New name (Leave it blank if you don’t want to change it.): ");
                        String newName = input.nextLine();

                        listBranch();

                        System.out.print("New branch (Leave it blank if you don’t want to change it.): ");
                        String newBranch = input.nextLine();

                        UpdateDoctorRequest doctorRequest = getUpdateDoctorRequest(newName, newBranch);
                        doctorService.updateDoctor(doctorId, doctorRequest);
                        break;
                    case 4:
                        doctorService.listDoctors();
                        break;
                    case 5:
                        isTrue = false;
                        break;
                    case 6:
                        System.out.println("---DOCTOR LIST---");
                        doctorService.listDoctors();

                        System.out.print("Enter Doctor id: ");
                        doctorId = input.nextInt();

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
                        input.nextLine();

                        DayOffType[] dayOffType = DayOffType.values();
                        DayOffType selectedType = dayOffType[choiceType - 1];

                        System.out.println("Enter a note : ");
                        String note = input.nextLine();

                        DoctorDayOff dayOff = new DoctorDayOff(doctorId, date, selectedType, note);
                        doctorService.addDayOff(dayOff);
                        System.out.println("Doctor's day off has been successfully recorded.");
                        break;
                    case 7:
                        System.out.println("DOCTOR LIST");
                        doctorService.listDoctors();

                        System.out.println("Enter Doctor ID : ");
                        int selectedDoctorId = input.nextInt();
                        input.nextLine();

                        System.out.println("Dr." + doctorService.findByDoctorId(selectedDoctorId).getFullName() + "'s Day Offs: ");
                        List<DoctorDayOff> dayOffs = doctorService.listDayOff(selectedDoctorId);

                        if(dayOffs.isEmpty()){
                            System.out.println("This doctor has no day off.");
                        } else {
                            dayOffs.forEach( off ->
                                    System.out.println(off.getDateTime() + " - " +
                                            off.getDayOffType() + " - " +
                                            off.getNote()));
                        }
                        break;
                    default:
                        System.out.println("Faulty input!!");
                        break;
                }
            } catch (ValidationException e) {
                System.out.println("Input error : " + e.getMessage());
            } catch (NotFoundException e) {
                System.out.println("Not found : " + e.getMessage());
            } catch (BusinessRuleException e) {
                System.out.println("Business rule error : " + e.getMessage());
            }
        }
    }

    private static UpdateDoctorRequest getUpdateDoctorRequest(String newName, String newBranch) {
        UpdateDoctorRequest doctorRequest = new UpdateDoctorRequest();

        if (!newName.isBlank()) {
            doctorRequest.setFullname(newName);
        }

        if (!newBranch.isBlank()) {
            int branchNum = Integer.parseInt(newBranch);
            Branch branch = Branch.values()[branchNum - 1];
            doctorRequest.setBranch(branch);
        }
        return doctorRequest;
    }

    public static void showTheMenu(){
        System.out.println("1-Add Doctor\n" +
                "2-Delete Doctor\n" +
                "3-Update Doctor\n" +
                "4-List Doctors\n" +
                "5-Back to Main Menu\n" +
                "6-Add Doctor Day Off\n" +
                "7-List Doctor Day Offs"
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



