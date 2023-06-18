package com.mocProject.inventory.service;

import com.mocProject.inventory.entity.Product;
import com.mocProject.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductServiceImp  implements  ProductService{
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product incrementProduct(int productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setQuantity(product.getQuantity() + 1);
            productRepository.save(product);
            return product;
        } else {
            throw new Exception("no entity product with this Id");
        }

    }

    @Override
    public Product decrementProduct(int productId) throws Exception {

            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                if (product.getQuantity() > 0) {
                    product.setQuantity(product.getQuantity() - 1);
                    if(product.getQuantity()<0)
                        product.setInStock(false);
                    productRepository.save(product);
                    return product;
                } else {
                    throw new Exception("Quantity is 0");
                }
            } else {
                throw new Exception("no entity product with this Id");
            }
        }

    @Override
    public Boolean checkProductAvailability(int productId) throws Exception {

            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                boolean isAvailable = product.getQuantity() > 0 || product.isInStock();
                return isAvailable;
            } else {
                throw new Exception("no entity product with this Id");
            }
        }



}
