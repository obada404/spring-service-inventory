package com.mocProject.inventory.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {

    private int Id;
    private String name;
    private String description;
    private Float price;
    private boolean inStock;
}
