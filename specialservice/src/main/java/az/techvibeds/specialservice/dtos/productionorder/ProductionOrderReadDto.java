package az.techvibeds.specialservice.dtos.productionorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderReadDto {
    private Long id;
    private String productName;
    private Integer quantity;
    private String orderStatus;
    private Date startDate;
    private Date finishDate;
    private Integer progressPercentage;
}
