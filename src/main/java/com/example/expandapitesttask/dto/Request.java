package com.example.expandapitesttask.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Request {
    private String table;
    private List<Record> records;
}
