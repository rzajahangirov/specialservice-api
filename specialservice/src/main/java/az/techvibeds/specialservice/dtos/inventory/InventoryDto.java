package az.techvibeds.specialservice.dtos.inventory;

import az.techvibeds.specialservice.dtos.product.ProductInventoryDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDto {
    private Long stockCount;
    private String growthRate;
    private Integer productCount;
    private String lastTransfer;
    private List<ProductInventoryDto> productInventoryDtoList;
}
