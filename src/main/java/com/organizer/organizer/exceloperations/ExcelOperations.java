package com.organizer.organizer.exceloperations;

import com.organizer.organizer.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class ExcelOperations {
    public static final String FILE_NAME = "organizer.xlsx";
    private TaskOperations taskOperations;

    public ExcelOperations(TaskOperations taskOperations) {
        this.taskOperations = taskOperations;
    }

    public void createFile() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet dailySheet = workbook.createSheet("Daily Tasks");
            Sheet weeklySheet = workbook.createSheet("Weekly Tasks");
            Sheet monthlySheet = workbook.createSheet("Monthly Tasks");
            Sheet yearlySheet = workbook.createSheet("Yearly Tasks");

            String[] headers = {"ID", "Task Name", "Date", "Progress", "Status"};

            createHeaderRow(dailySheet, headers);
            createHeaderRow(weeklySheet, headers);
            createHeaderRow(monthlySheet, headers);
            createHeaderRow(yearlySheet, headers);


            try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME)) {
                workbook.write(fileOut);
            }

            log.info("() has been created", FILE_NAME);

        } catch (IOException ex) {
            log.info("problem with creating file - ()", FILE_NAME);
            ex.printStackTrace();
        }
    }


    public void addTaskToFile() {
        Task task = taskOperations.addNewTask();

        try (FileInputStream fis = new FileInputStream("organizer.xlsx")) {

            Workbook workbook = new XSSFWorkbook(fis);
            //0-daily
            //1-weekly
            //2-monthly
            //3-yearly
            Sheet sheet;

            switch (task.getType()) {
                case "Daily" -> {
                    sheet = workbook.getSheet("Daily Tasks");
                }
                case "Weekly" -> {
                    sheet = workbook.getSheet("Weekly Tasks");
                }
                case "Monthly" -> {
                    sheet = workbook.getSheet("Monthly Tasks");
                }
                case "Yearly" -> {
                    sheet = workbook.getSheet("Yearly Tasks");
                }
                default -> sheet = workbook.getSheet("Daily Tasks");
            }

            int rowIndex = sheet.getPhysicalNumberOfRows();
            Row row = sheet.createRow(rowIndex);

            row.createCell(0).setCellValue(task.getId());
            row.createCell(1).setCellValue(task.getTaskName());
            row.createCell(2).setCellValue(task.getCreatedDate().toString());
            row.createCell(3).setCellValue(task.getProgress());
            row.createCell(4).setCellValue(task.getStatus());


            try(FileOutputStream fos = new FileOutputStream("organizer.xlsx")){
                workbook.write(fos);
            }

        } catch (IOException ex) {
            log.info("Problem with file ()", FILE_NAME);
            ex.getMessage();
        }

    }


    private static void createHeaderRow(Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);

        }
    }


}
