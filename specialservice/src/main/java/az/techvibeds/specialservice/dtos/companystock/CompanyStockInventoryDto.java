package az.techvibeds.specialservice.dtos.companystock;

import lombok.*;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyStockInventoryDto {
    private Long stockCount;
    private String growthRate;
}
