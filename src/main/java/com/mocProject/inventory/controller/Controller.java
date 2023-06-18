package com.mocProject.inventory.controller;

import com.mocProject.inventory.DTO.ProductDTO;
import com.mocProject.inventory.entity.Product;
import com.mocProject.inventory.repository.ProductRepository;
import com.mocProject.inventory.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory")
public class Controller {

    private ProductRepository productRepository;
    private ProductService productService;
    private ModelMapper modelMapper;

    @Autowired
    public Controller(ProductRepository productRepository, ProductService productService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/products/byIds")
    public List<ProductDTO> getProductsByIds(@RequestParam("ids") List<Integer> ids) {
        List<Product> products = productRepository.findByIdIn(ids);
        List<ProductDTO> productDTOs = getProductDTOS(products);
        return productDTOs;
    }

    @PostMapping("products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        Product createdProduct = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(createdProduct,ProductDTO.class));
    }

    @GetMapping("/products/{productId}")
    public ProductDTO getProduct(@PathVariable("productId") int productId) {
        Product product = productRepository.findById(productId).get();
        return modelMapper.map(product,ProductDTO.class);
    }

    @GetMapping("/products")
    public Page<ProductDTO> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductDTO> productDTOPage = products.map(product -> modelMapper.map(product, ProductDTO.class));
        return productDTOPage;
    }

    // you can put product id in the url and check it before update
    @PutMapping("/products")
    public ResponseEntity<ProductDTO> updateProduct(
                                                 @RequestBody Product product) {

        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(modelMapper.map(updatedProduct,ProductDTO.class));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") int productId) {
        productRepository.deleteById(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }



    @GetMapping("/products/count")
    public ResponseEntity<Integer> getProductCount() {
        int count = (int) productRepository.count();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productRepository.findByCategory(category);
        List<ProductDTO> productDTOs = getProductDTOS(products);
        return ResponseEntity.ok(productDTOs);
    }



    @PutMapping("/products/{productId}/increment")
    public ProductDTO incrementProductQuantity(@PathVariable int productId) throws Exception {
        return modelMapper.map(productService.incrementProduct(productId),ProductDTO.class);

    }
    @PutMapping("/products/{productId}/decrement")
    public ProductDTO decrementProductQuantity(@PathVariable int productId) throws Exception {
        return modelMapper.map(productService.decrementProduct(productId),ProductDTO.class);
    }
    @GetMapping("/products/{productId}/availability")
    public ResponseEntity<Boolean> checkProductAvailability(@PathVariable int productId) throws Exception {
        return  ResponseEntity.ok(productService.checkProductAvailability(productId));
    }
    private List<ProductDTO> getProductDTOS(List<Product> products) {
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        return productDTOs;
    }



}
