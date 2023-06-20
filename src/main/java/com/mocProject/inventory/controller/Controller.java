package com.mocProject.inventory.controller;

import com.mocProject.inventory.DTO.ProductDTO;
import com.mocProject.inventory.entity.Product;
import com.mocProject.inventory.repository.ProductRepository;
import com.mocProject.inventory.service.ProductService;
import jakarta.validation.Valid;
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
@RequestMapping("/inventory/products")
public class Controller {

    final private ProductRepository productRepository;
    final private ProductService productService;
    final private ModelMapper modelMapper;

    @Autowired
    public Controller(ProductRepository productRepository, ProductService productService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/byIds")
    public List<ProductDTO> getProductsByIds(@RequestParam("ids") List<Integer> ids) {
        List<Product> products = productRepository.findByIdIn(ids);
        List<ProductDTO> productDTOs = getProductDTOS(products);
        return productDTOs;
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody  ProductDTO  productDTO) {
        Product createdProduct = productRepository.save(modelMapper.map(productDTO,Product.class));
            if(createdProduct!= null)
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(createdProduct,ProductDTO.class));
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{productId}")
    public ProductDTO getProduct(@PathVariable("productId") int productId) {
        Product product = productRepository.findById(productId).get();
        return modelMapper.map(product,ProductDTO.class);
    }

    @GetMapping("")
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
    @PutMapping("")
    public ResponseEntity<ProductDTO> updateProduct(
                                                 @RequestBody Product product) {

        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(modelMapper.map(updatedProduct,ProductDTO.class));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") int productId) {
        productRepository.softDeleteById(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }


    @GetMapping("/count")
    public ResponseEntity<Integer> getProductCount() {
        int count = (int) productRepository.count();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productRepository.findByCategory(category);
        List<ProductDTO> productDTOs = getProductDTOS(products);
        return ResponseEntity.ok(productDTOs);
    }



    @PutMapping("/{productId}/increment")
    public ProductDTO incrementProductQuantity(@PathVariable int productId) throws Exception {
        return modelMapper.map(productService.incrementProduct(productId),ProductDTO.class);

    }
    @PutMapping("/{productId}/decrement")
    public ProductDTO decrementProductQuantity(@PathVariable int productId) throws Exception {
        return modelMapper.map(productService.decrementProduct(productId),ProductDTO.class);
    }
    @GetMapping("/{productId}/availability")
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
