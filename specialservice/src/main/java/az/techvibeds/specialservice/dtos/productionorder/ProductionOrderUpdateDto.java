package az.techvibeds.specialservice.dtos.productionorder;

import az.techvibeds.specialservice.dtos.manufacturedproduct.ManufacturedProductUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderUpdateDto {
    private Integer quantity;
    private String orderStatus;
    private Date startDate;
    private Date finishDate;
    private Integer progressPercentage;
    private ManufacturedProductUpdateDto product;
}
