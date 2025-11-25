package az.techvibeds.specialservice.dtos.semiproduct;

import az.techvibeds.specialservice.enums.SemiProductStatus;
import az.techvibeds.specialservice.enums.UnitType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemiProductReadDto {
    private Long id;
    private String name;
    private String description;
    private String  unitType;
    private Integer stockQuantity;
    private String productStatus;
}
