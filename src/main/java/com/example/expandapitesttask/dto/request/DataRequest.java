package com.example.expandapitesttask.dto.request;

import com.example.expandapitesttask.dto.Record;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DataRequest {
    private String table;
    private List<Record> records;
}
