package com.mocProject.inventory.service;

import com.mocProject.inventory.entity.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    Product incrementProduct(int productId) throws Exception;

    Product decrementProduct(int productId) throws Exception;

    Boolean checkProductAvailability(int productId) throws Exception;
}
