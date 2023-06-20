package com.mocProject.inventory.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {

    @NotNull
    @NotEmpty
    private String name;
    private String description;
    @NotNull
    @DecimalMin(value = "0.0")
    private Float price;
    private boolean inStock;
}
