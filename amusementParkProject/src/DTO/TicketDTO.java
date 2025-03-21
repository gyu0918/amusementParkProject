package javaproject.DTO;
import lombok.*;

@Getter
@Builder
public class TicketDTO {
    private String tPass;
    private String tName;
    private int tPrice;
}
