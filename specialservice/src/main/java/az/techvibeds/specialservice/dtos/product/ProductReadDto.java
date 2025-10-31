package az.techvibeds.specialservice.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReadDto {
    private Long id;
    private Long warehouseProductId;
    private String name;
    private String productCode;
    private Long totalStock;
    private BigDecimal price;
    private String categoryName;
    private String productStatus;
    private String warehouseName;
    private Integer quantity;
}
