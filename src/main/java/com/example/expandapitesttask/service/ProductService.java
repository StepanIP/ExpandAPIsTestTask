package com.example.expandapitesttask.service;

import com.example.expandapitesttask.dto.Record;
import com.example.expandapitesttask.dto.Request;

import java.util.List;

public interface ProductService {
    void saveRecords(Request request);
    List<Record> getAll();
}
