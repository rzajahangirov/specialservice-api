package az.techvibeds.specialservice.dtos.productionorder;

import az.techvibeds.specialservice.dtos.manufacturedproduct.ManufacturedProductCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderCreateDto {
    private Integer quantity;
    private String orderStatus;
    private Date startDate;
    private Date finishDate;
    private ManufacturedProductCreateDto manufacturedProduct;
}
