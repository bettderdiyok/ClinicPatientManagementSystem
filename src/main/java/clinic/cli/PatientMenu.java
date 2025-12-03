package clinic.cli;

import clinic.dto.UpdatePatientRequest;
import clinic.exception.BusinessRuleException;
import clinic.exception.NotFoundException;
import clinic.exception.ValidationException;
import clinic.service.PatientService;

import java.util.Scanner;

public class PatientMenu {
    Scanner input = new Scanner(System.in);
    private final PatientService patientService;

    public PatientMenu(PatientService patientService) {
        this.patientService = patientService;
    }

    public void showThePatientMenu() {
        int choice;
        boolean isTrue = true;
        boolean hasGuardian = false;
        while (isTrue) {
            System.out.println("------MENU-----");
            showTheMenu();
            System.out.print("Choose the process that you want to perform : ");
            choice = input.nextInt();
            input.nextLine();
            int patientId;
            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter patient name: ");
                        String name = input.nextLine();

                        System.out.print("Enter national ID: ");
                        String nationalId = input.nextLine();

                        System.out.print("Enter age : ");
                        int age = input.nextInt();
                        input.nextLine();

                        if (age < 18) {
                            System.out.print("Is a guardian present? (yes / no) ");
                            String answer = input.nextLine().trim().toLowerCase();
                            hasGuardian = answer.equalsIgnoreCase("yes");
                            if (!hasGuardian) {
                                System.out.println("Patients under 18 must have a guardian. Returning to menu...");
                                break;
                            }
                        }

                        System.out.print("Enter patient complaint: ");
                        String complain = input.nextLine();

                        patientService.addPatient(name, nationalId, age, hasGuardian, complain);
                        System.out.println("The patient added");
                        break;
                    case 2:
                        System.out.println("------- PATIENT LIST ------");
                        patientService.listPatients();
                        System.out.print("Enter patient ID : ");
                        patientId = input.nextInt();
                        patientService.deletePatient(patientId);
                        System.out.println("Patient deleted.");
                        break;
                    case 3:
                        System.out.println("------- PATIENT LIST ------");
                        patientService.listPatients();
                        System.out.print("Enter patient ID : ");
                        patientId = input.nextInt();
                        input.nextLine();

                        System.out.print("New name (Leave it blank if you don’t want to change it.): ");
                        String fullName = input.nextLine();

                        System.out.print("New age (Leave it blank if you don’t want to change it.): ");
                        String ageInput = input.nextLine();

                        System.out.print("New complaint (Leave it blank if you don't want to change it.): ");
                        String complaintInput = input.nextLine();

                        UpdatePatientRequest request = getUpdatePatientRequest(fullName, ageInput, complaintInput);
                        patientService.updatePatient(patientId, request);
                        System.out.println("Patient is updated");
                        break;
                    case 4:
                        patientService.listPatients();
                        break;
                    case 5:
                        showTheMenu();
                        break;
                    case 6:
                        isTrue = false;
                        break;
                }
            } catch (NotFoundException | ValidationException | BusinessRuleException e) {
                System.out.println(e.getMessage());
            }
        }
    }

        private static UpdatePatientRequest getUpdatePatientRequest (String fullName, String ageInput, String
        complaintInput){
            UpdatePatientRequest request = new UpdatePatientRequest();

            if (!fullName.isBlank()) {
                request.setFullname(fullName);
            }

            if (!ageInput.isBlank()) {
                int age = Integer.parseInt(ageInput);
                request.setAge(age);
            }

            if (!complaintInput.isBlank()) {
                request.setComplaint(complaintInput);
            }
            return request;
        }

        public static void showTheMenu () {
            System.out.println("""
                    1-Add New Patient
                    2-Delete Patients
                    3-Update Patients
                    4-List All Patients
                    5-Menu Again
                    6-Back to Main Menu
                    """
            );
        }
    }

