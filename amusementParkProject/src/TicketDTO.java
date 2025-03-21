package javaproject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {
    private String ticketNum;
    private String ticketType;
    private String ticketContent;
    private int ticketPrice;
    private String userID;

}
