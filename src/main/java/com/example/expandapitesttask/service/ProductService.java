package com.example.expandapitesttask.service;

import com.example.expandapitesttask.dto.Record;
import com.example.expandapitesttask.dto.request.DataRequest;

import java.util.List;

public interface ProductService {
    void saveRecords(DataRequest dataRequest);
    List<Record> getAll();
}
