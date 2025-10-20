package az.techvibeds.specialservice.dtos.product;


import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDto {
    private String name;
    private String productCode;
    private Long stock;
    private BigDecimal price;
    private String category;
    private String productStatus;
    private String warehouseName;
    private Integer quantity;
}