package com.organizer.organizer.exceloperations;

import com.organizer.organizer.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Scanner;

@Service
public class TaskOperations {
    public Task addNewTask() {
        Task task = new Task();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type Task Name");
        String taskName = scanner.next();
        task.setTaskName(taskName);

        System.out.println("Task Type D/W/M/Y");
        String taskType = scanner.next();


        switch (taskType) {
            case "D" -> {
                task.setType("Daily");
            }
            case "W" -> {
                task.setType("Weekly");
            }
            case "M" -> {
                task.setType("Monthly");
            }
            case "Y" -> {
                task.setType("Yearly");
            }
            default -> {
                task.setType("Daily");
            }
        }

        task.setId(1); //todo
        task.setStatus("IN PROGRESS");
        task.setProgress("0%");
        task.setCreatedDate(LocalDateTime.now());

        System.out.println("newTASK -> " + task.toString());

        return task;
    }
}
