package javaproject.DTO;


import lombok.*;

@Getter
@Builder
public class AttractionDTO {
    String atId;
    String atName;
    String atUrl;
    int atMax;
    int atOnoff;
}