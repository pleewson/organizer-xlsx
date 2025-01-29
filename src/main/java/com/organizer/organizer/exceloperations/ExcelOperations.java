package com.organizer.organizer.exceloperations;

import com.organizer.organizer.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class ExcelOperations {
    public static final String FILE_NAME = "organizer.xlsx";
    private TaskService taskOperations;

    public ExcelOperations(TaskService taskOperations) {
        this.taskOperations = taskOperations;
    }


    public void start() {
        Path filePath = Paths.get("organizer.xlsx");
        if (Files.exists(filePath)) {
            log.info("loading data..");
            //load all data
        } else {
            createFile();
        }
    }

    public void createFile() { //todo -> private
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

            log.info("creating new .xlsx file..");

        } catch (IOException ex) {
            log.info("problem with creating file - ()", FILE_NAME);
            ex.printStackTrace();
        }
    }


    public void addTaskToFile() {
        Task task = taskOperations.addNewTask();

        try (FileInputStream fis = new FileInputStream(FILE_NAME)) {

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


            try (FileOutputStream fos = new FileOutputStream("organizer.xlsx")) {
                workbook.write(fos);
            }

            System.out.println("new task has been added.");

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


    protected static long getHighestId() {
        long highestId = 0;
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             Workbook workbook = new XSSFWorkbook(fis)) {


            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Row firstRow = sheet.getRow(0);
                if (highestId < firstRow.getLastCellNum() - 1) {
                    highestId = firstRow.getLastCellNum() - 1;
                }
            }
            log.info("highest ID is -> {}", highestId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return highestId+1;
    }
}
