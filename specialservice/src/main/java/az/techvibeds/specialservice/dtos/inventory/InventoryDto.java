package az.techvibeds.specialservice.dtos.inventory;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDto {
    private Long stockCount;
    private Double growthRate;

}
