package com.example.expandapitesttask.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Record {

    private Long id;
    private LocalDate entryDate;
    private String itemCode; //this field could be very long and I don`t see the functionality to make math action with this in future
    private String itemName;
    private String itemQuantity; //I don`t see the functionality to make math action with this
    private String status;

}
