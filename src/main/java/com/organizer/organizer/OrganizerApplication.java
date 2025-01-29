package com.organizer.organizer;

import com.organizer.organizer.exceloperations.ExcelOperations;
import com.organizer.organizer.menu.Menu;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class OrganizerApplication {

    private final Menu menu;
    private final ExcelOperations excelOperations;

    public OrganizerApplication(Menu menu, ExcelOperations excelOperations) {
        this.menu = menu;
        this.excelOperations = excelOperations;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrganizerApplication.class, args);
    }

    @PostConstruct
    public void init(){


        excelOperations.start();
        menu.openMenu();
    }

    //todo fix incrementing id

}
