package az.techvibeds.specialservice.dtos.companyStock;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyStockInventoryDto {
    private LocalDate monthDate;
    private Long stockCount;
}
