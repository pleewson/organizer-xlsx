package com.organizer.organizer;

import com.organizer.organizer.exceloperations.ExcelOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class OrganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizerApplication.class, args);

		ExcelOperations excelOperations = new ExcelOperations();
		excelOperations.createFile();
	}

}
