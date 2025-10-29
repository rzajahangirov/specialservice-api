package az.techvibeds.specialservice.dtos.unit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitReadDto {
    private Long id;
    private String name;
}
