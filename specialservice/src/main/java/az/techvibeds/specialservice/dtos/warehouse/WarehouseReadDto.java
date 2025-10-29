package az.techvibeds.specialservice.dtos.warehouse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseReadDto {
    private Long id;
    private String name;
    private String address;
    private String companyName;
}
