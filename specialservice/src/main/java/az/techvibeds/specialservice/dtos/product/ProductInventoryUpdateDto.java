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
public class ProductInventoryUpdateDto {
    private Long id;
    private String productCode;
    private String name;
    private Integer stock;
    private BigDecimal price;
    private String warehouse;
    private String category;
    private String productStatus;
}
