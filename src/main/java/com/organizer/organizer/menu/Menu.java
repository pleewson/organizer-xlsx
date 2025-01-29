package com.organizer.organizer.menu;

import com.organizer.organizer.exceloperations.ExcelOperations;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private final ExcelOperations excelOperations;

    public Menu(ExcelOperations excelOperations) {
        this.excelOperations = excelOperations;
    }

    public void openMenu() {
        System.out.println("WELCOME IN YOUR ORGANIZER!");
        firstMenu();
    }

    private void firstMenu() {

        System.out.println("1. Check your tasks");
        System.out.println("2. Add new task");

        String chosenNumber = scanner.next();
        boolean correct = false;

        while (!correct) {
            if (chosenNumber.equals("1")) {
                break;
            } else if (chosenNumber.equals("2")) {
                break;
            } else if (chosenNumber.equals("quit")) {
                break;
            }

            System.out.println("incorrect input, try again");
            chosenNumber = scanner.next();
        }

        switch (chosenNumber) {
            case "1" -> {
                System.out.println("IM CHECKING YOUR TASKS...");
            }
            case "2" -> {
                excelOperations.addTaskToFile();
                firstMenu();

            }
            case "quit" -> {
                System.out.println("saving.. see you next time!");
            }
        }

    }

}
