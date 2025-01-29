package com.organizer.organizer.exceloperations;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class ExcelOperations {
    public static final String FILE_NAME = "organizer.xlsx";

    public void createFile() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet dailySheet = workbook.createSheet("Daily Tasks");
            Sheet weeklySheet = workbook.createSheet("Weekly Tasks");
            Sheet monthlySheet = workbook.createSheet("Monthly Tasks");
            Sheet yearlySheet = workbook.createSheet("Yearly Tasks");

            String[] headers = {"ID", "Task Name", "Date/Month/Year", "Progress", "Status"};

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

    private static void createHeaderRow(Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);

        }
    }


}
