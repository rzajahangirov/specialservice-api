package az.techvibeds.specialservice.dtos.manufacturing;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturingManagementDto {
    private Integer inProductionOrderCount;
    private Integer last24HoursOrderCount;
    private Integer monthlyProductionCount;
    private String relativeToLastMonth;
    private Integer defectiveProducts;
    private Integer target;
    private Map<String, Integer> productionsCountsMonthly;
    private Map<String, Integer> costsCountsMonthly;
}
