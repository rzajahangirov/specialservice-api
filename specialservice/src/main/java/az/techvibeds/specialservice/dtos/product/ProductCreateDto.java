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
    private BigDecimal price;
    private Long categoryId;
    private String productStatus;
    private Long warehouseId;
    private Integer quantity;
}