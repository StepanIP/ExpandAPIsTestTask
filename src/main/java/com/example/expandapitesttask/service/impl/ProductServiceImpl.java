package com.example.expandapitesttask.service.impl;

import com.example.expandapitesttask.dto.Record;
import com.example.expandapitesttask.dto.request.DataRequest;
import com.example.expandapitesttask.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private String tableName;

    public void saveRecords(DataRequest dataRequest) {
        tableName = dataRequest.getTable();
        List<Record> records = dataRequest.getRecords();

        createTableIfNotExists(tableName);

        for (Record record : records) {
            StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");
            StringBuilder values = new StringBuilder(" VALUES (");

            insertQuery.append("entryDate, itemCode, itemName, itemQuantity, status");
            values.append("'").append(LocalDate.parse(record.getEntryDate(), formatter)).append("', ")
                    .append("'").append(record.getItemCode()).append("', ")
                    .append("'").append(record.getItemName()).append("', ")
                    .append("'").append(record.getItemQuantity()).append("', ")
                    .append("'").append(record.getStatus()).append("', ");

            insertQuery.setLength(insertQuery.length());
            values.setLength(values.length() - 2);

            insertQuery.append(")").append(values).append(")");

            Query query = entityManager.createNativeQuery(insertQuery.toString());
            query.executeUpdate();
        }
    }

    public List<Record> getAll() {
        String selectQuery = "SELECT * FROM " + tableName;
        Query query = entityManager.createNativeQuery(selectQuery);
        List<Object[]> resultList = query.getResultList();

        List<String[]> stringResultList = new ArrayList<>();
        for (Object[] row : resultList) {
            String[] stringRow = new String[row.length];
            for (int i = 0; i < row.length; i++) {
                stringRow[i] = String.valueOf(row[i]);
            }
            stringResultList.add(stringRow);
        }

        return convertToRecords(stringResultList);
    }

    private List<Record> convertToRecords(List<String[]> stringResultList) {
        List<Record> recordList = new ArrayList<>();
        for (String[] stringRow : stringResultList) {
            Record record = new Record();
            record.setEntryDate(stringRow[1]);
            record.setItemCode(stringRow[2]);
            record.setItemName(stringRow[3]);
            record.setItemQuantity(stringRow[4]);
            record.setStatus(stringRow[5]);
            recordList.add(record);
        }
        return recordList;
    }

    private void createTableIfNotExists(String tableName) {
        try {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                              "id SERIAL PRIMARY KEY, " +
                              "entryDate DATE, " +
                              "itemCode VARCHAR(255), " +
                              "itemName VARCHAR(255), " +
                              "itemQuantity VARCHAR(255), " +
                              "status VARCHAR(255))";
            Query query = entityManager.createNativeQuery(sqlQuery);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}