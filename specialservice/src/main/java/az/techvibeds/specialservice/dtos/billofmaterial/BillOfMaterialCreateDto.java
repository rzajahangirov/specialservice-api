package az.techvibeds.specialservice.dtos.billofmaterial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillOfMaterialCreateDto {
    private Long productId;
    private String name;
    private String bomStatus;
}
