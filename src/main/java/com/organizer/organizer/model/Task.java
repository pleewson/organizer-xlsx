package com.organizer.organizer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Setter
@Getter
@ToString
public class Task {
    long id;
    String taskName;
    LocalDateTime createdDate;
    String progress;
    String status;
    String type;
}
