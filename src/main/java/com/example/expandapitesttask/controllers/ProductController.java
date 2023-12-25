package com.example.expandapitesttask.controllers;

import com.example.expandapitesttask.dto.Record;
import com.example.expandapitesttask.dto.Request;
import com.example.expandapitesttask.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products") //usually it is /api/v1/ but I did as in the instruction
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProducts(@RequestBody Request request) {
        productService.saveRecords(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<List<Record>> getProducts() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

}
