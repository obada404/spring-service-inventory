package com.mocProject.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private Float price;
    private String category;
    private int quantity;
    private boolean inStock;
    @Column(name = "is_deleted")
    private boolean deleted;
    private Date CreatedAt;
    private Date UpdatedAt;

}
