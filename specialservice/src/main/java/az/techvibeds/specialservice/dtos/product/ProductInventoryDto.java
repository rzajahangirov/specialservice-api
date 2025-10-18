package az.techvibeds.specialservice.dtos.product;

import az.techvibeds.specialservice.enums.ProductStatus;
import az.techvibeds.specialservice.models.Category;
import az.techvibeds.specialservice.models.Warehouse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInventoryDto {
    private String productCode;
    private String name;
    private Long stock;
    private BigDecimal price;
    private String warehouse;
    private String category;
    private String productStatus;
}
