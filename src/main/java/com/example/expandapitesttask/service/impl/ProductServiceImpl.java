package com.example.expandapitesttask.service.impl;

import com.example.expandapitesttask.dto.Record;
import com.example.expandapitesttask.dto.Request;
import com.example.expandapitesttask.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    private String tableName;

    public void saveRecords(Request request) {
        tableName = request.getTable();
        List<Record> records = request.getRecords();

        createTableIfNotExists(tableName);

        for (Record record : records) {
            StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");
            StringBuilder values = new StringBuilder(" VALUES (");

            insertQuery.append("entryDate, itemCode, itemName, itemQuantity, status");
            values.append("'").append(record.getEntryDate()).append("', ")
                    .append("'").append(record.getItemCode()).append("', ")
                    .append("'").append(record.getItemName()).append("', ")
                    .append("'").append(record.getItemQuantity()).append("', ")
                    .append("'").append(record.getStatus()).append("', ");

            insertQuery.setLength(insertQuery.length() - 2);
            values.setLength(values.length() - 2);

            insertQuery.append(")").append(values).append(")");

            Query query = entityManager.createNativeQuery(insertQuery.toString());
            query.executeUpdate();
        }
    }

    public List<Record> getAll() {
        String selectQuery = "SELECT * FROM " + tableName;
        Query query = entityManager.createNativeQuery(selectQuery, Record.class);
        return (List<Record>) query.getResultList();
    }

    private void createTableIfNotExists(String tableName) {
        Query query = entityManager.createNativeQuery(
                "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "entryDate DATE, " +
                "itemCode VARCHAR(255), " +
                "itemName VARCHAR(255), " +
                "itemQuantity VARCHAR(255), " +
                "status VARCHAR(255))"
        );
        query.executeUpdate();
    }
}