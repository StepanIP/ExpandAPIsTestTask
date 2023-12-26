package com.example.expandapitesttask.service;

import com.example.expandapitesttask.ExpandApiTestTaskApplication;
import com.example.expandapitesttask.dto.Record;
import com.example.expandapitesttask.dto.request.DataRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest(classes= ExpandApiTestTaskApplication.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testSaveAndGetRecords() {
        DataRequest dataRequest = new DataRequest();
        dataRequest.setTable("test_table");

        Record record1 = new Record();
        record1.setEntryDate("03-01-2023");
        record1.setItemCode("123");
        record1.setItemName("Test Item 1");
        record1.setItemQuantity("10");
        record1.setStatus("NEW");

        Record record2 = new Record();
        record2.setEntryDate("03-01-2023");
        record2.setItemCode("456");
        record2.setItemName("Test Item 2");
        record2.setItemQuantity("5");
        record2.setStatus("OLD");

        dataRequest.setRecords(Arrays.asList(record1, record2));

        productService.saveRecords(dataRequest);

        List<Record> allRecords = productService.getAll();
        assertEquals(2, allRecords.size());
        assertEquals(record1.getItemCode(), allRecords.get(0).getItemCode());
        assertEquals(record2.getItemCode(), allRecords.get(1).getItemCode());
    }
}
