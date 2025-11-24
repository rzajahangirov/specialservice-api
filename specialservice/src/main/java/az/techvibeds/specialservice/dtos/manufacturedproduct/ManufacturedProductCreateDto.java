package az.techvibeds.specialservice.dtos.manufacturedproduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturedProductCreateDto {
    private String name;
    private String description;
}
