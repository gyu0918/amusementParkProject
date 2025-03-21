package javaproject.DTO;

import lombok.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class TimeTableDTO {
    private String tiId;
    private int tiDay;
    private Time tiTime;
    private String tiContent;
}
