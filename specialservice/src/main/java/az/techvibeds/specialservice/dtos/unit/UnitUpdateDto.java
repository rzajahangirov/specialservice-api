package az.techvibeds.specialservice.dtos.unit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitUpdateDto {
    private Long id;
    private String name;
}
