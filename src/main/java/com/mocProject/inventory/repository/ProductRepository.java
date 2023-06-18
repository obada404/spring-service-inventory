package com.mocProject.inventory.repository;

import com.mocProject.inventory.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource(exported = false)

public interface ProductRepository extends JpaRepository<Product,Integer> {


    List<Product> findByIdIn(List<Integer> ids);

    Page<Product> findAll(Pageable pageable);

    List<Product> findByCategory(String category);


}
